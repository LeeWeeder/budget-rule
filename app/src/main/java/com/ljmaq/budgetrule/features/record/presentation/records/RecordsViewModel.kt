package com.ljmaq.budgetrule.features.record.presentation.records

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ljmaq.budgetrule.features.record.domain.model.Category
import com.ljmaq.budgetrule.features.record.domain.model.Record
import com.ljmaq.budgetrule.features.record.domain.usecase.RecordsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordsViewModel @Inject constructor(
    private val recordsUseCases: RecordsUseCases
) : ViewModel() {
    private val _state = mutableStateOf(RecordState())
    val state: State<RecordState> = _state

    private val _categoryState = mutableStateOf(CategoryState(selectedCategory = 0))
    val categoryState: State<CategoryState> = _categoryState

    private val _isAddRecordDialogShowing = mutableStateOf(false)
    val isAddRecordDialogShowing: State<Boolean> = _isAddRecordDialogShowing

    private val _isOnSelectionMode = mutableStateOf(false)
    val isOnSelectionMode: State<Boolean> = _isOnSelectionMode

    private val _selectedRecords = mutableStateListOf<Record>()
    val selectedRecords: SnapshotStateList<Record> = _selectedRecords

    private var recentlyDeletedRecord: SnapshotStateList<Record> = mutableStateListOf()

    private var getRecordsJob: Job? = null

    init {
        getRecords()
    }

    fun onEvent(event: RecordsEvent) {
        when(event) {
            is RecordsEvent.DeleteRecord -> {
                viewModelScope.launch {
                    recordsUseCases.deleteRecord(event.record)
                    recentlyDeletedRecord.add(event.record)
                }
            }
            is RecordsEvent.RestoreRecord -> {
                viewModelScope.launch {
                    recentlyDeletedRecord.forEach { record ->
                        recordsUseCases.addRecord(record)
                    }
                    recentlyDeletedRecord.clear()
                }
            }
            is RecordsEvent.ChangeCategory -> {
                _categoryState.value = categoryState.value.copy(
                    selectedCategory = Category.categories.indexOf(event.category)
                )
            }
            is RecordsEvent.CreateRecord -> {
                _isAddRecordDialogShowing.value = !isAddRecordDialogShowing.value
            }
            is RecordsEvent.CancelCreateRecord -> {
                _isAddRecordDialogShowing.value = false
            }

            is RecordsEvent.AddToSelection -> {
                if (!selectedRecords.contains(event.record)) {
                    _selectedRecords.add(event.record)
                }
            }

            is RecordsEvent.RemoveFromSelection -> {
                if (selectedRecords.contains(event.record)) {
                    _selectedRecords.remove(event.record)
                }
            }

            is RecordsEvent.ChangeSelectionMode -> {
                if (isOnSelectionMode.value) {
                    _selectedRecords.clear()
                }
                _isOnSelectionMode.value = !isOnSelectionMode.value
            }

            is RecordsEvent.ResetRecentlyDeletedRecord -> {
                recentlyDeletedRecord.clear()
            }

            is RecordsEvent.AddAllToSelection -> {
                state.value.records.forEach { record ->
                    if (!selectedRecords.contains(record)) {
                        _selectedRecords.add(record)
                    }
                }
            }

            is RecordsEvent.RemoveAllFromSelection -> {
                _selectedRecords.removeAll(state.value.records)
            }
        }
    }

    private fun getRecords() {
        getRecordsJob?.cancel()
        getRecordsJob = recordsUseCases.getRecords()
            .onEach { records ->
                _state.value = state.value.copy(
                    records = records
                )
            }
            .launchIn(viewModelScope)
    }
}