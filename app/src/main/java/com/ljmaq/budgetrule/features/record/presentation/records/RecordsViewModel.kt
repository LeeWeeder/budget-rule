package com.ljmaq.budgetrule.features.record.presentation.records

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

    private var recentlyDeletedRecord: Record? = null

    private var getRecordsJob: Job? = null

    init {
        getRecords()
    }

    fun onEvent(event: RecordsEvent) {
        when(event) {
            is RecordsEvent.DeleteRecord -> {
                viewModelScope.launch {
                    recordsUseCases.deleteRecord(event.record)
                    recentlyDeletedRecord = event.record
                }
            }
            is RecordsEvent.RestoreRecord -> {
                viewModelScope.launch {
                    recordsUseCases.addRecord(recentlyDeletedRecord ?: return@launch)
                    recentlyDeletedRecord = null
                }
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