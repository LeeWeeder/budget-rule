package com.ljmaq.budgetrule.features.record.presentation.records

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _dialogState = mutableStateOf(DialogState())
    val dialogState: State<DialogState> = _dialogState

    private val _isOnSelectionMode = mutableStateOf(false)
    val isOnSelectionMode: State<Boolean> = _isOnSelectionMode

    private val _selectedRecords = mutableStateListOf<Record>()
    val selectedRecords: SnapshotStateList<Record> = _selectedRecords

    private val _selectedCategory = mutableStateOf(CategoryState())
    val selectedCategory: State<CategoryState> = _selectedCategory

    private var recentlyDeletedRecord: SnapshotStateList<Record> = mutableStateListOf()

    private var getRecordsJob: Job? = null

    var currentRecord: Record? = null

    init {
        getRecords()

    }

    fun onEvent(event: RecordsEvent) {
        when (event) {
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

            is RecordsEvent.CategoryClick -> {
                _dialogState.value = dialogState.value.copy(
                    isAddExpenseRecordDialogOpen = true
                )
                _selectedCategory.value = selectedCategory.value.copy(
                    selectedCategory = event.category
                )
            }

            is RecordsEvent.CreateRecord -> {
                _dialogState.value = dialogState.value.copy(
                    isAddRecordDialogOpen = true
                )
            }

            is RecordsEvent.CancelCreateRecord -> {
                _dialogState.value = dialogState.value.copy(
                    isAddRecordDialogOpen = false
                )
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

            is RecordsEvent.CancelEditRecord -> {
                _dialogState.value = dialogState.value.copy(
                    isEditRecordDialogOpen = false
                )
            }

            is RecordsEvent.EditRecord -> {
                _dialogState.value = dialogState.value.copy(
                    isEditRecordDialogOpen = true
                )
                currentRecord = event.record
            }

            RecordsEvent.CategoryDialogDismiss -> {
                _dialogState.value = dialogState.value.copy(
                    isAddExpenseRecordDialogOpen = false
                )
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