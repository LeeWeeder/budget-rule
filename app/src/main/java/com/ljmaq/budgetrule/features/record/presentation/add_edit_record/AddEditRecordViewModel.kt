package com.ljmaq.budgetrule.features.record.presentation.add_edit_record

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ljmaq.budgetrule.features.record.domain.model.InvalidRecordException
import com.ljmaq.budgetrule.features.record.domain.model.Record
import com.ljmaq.budgetrule.features.record.domain.usecase.RecordsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditRecordViewModel @Inject constructor(
    private val recordsUseCases: RecordsUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _typeIsExpenses = mutableStateOf(
        false
    )

    val typeIsExpenses: State<Boolean> = _typeIsExpenses

    private val _recordAmount = mutableStateOf(
        RecordState.AmountTextFieldState()
    )

    val recordAmount: State<RecordState.AmountTextFieldState> = _recordAmount

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentRecordId: Int? = null

    init {
        savedStateHandle.get<Int>("recordId")?.let { recordId ->
            if (recordId != -1) {
                viewModelScope.launch {
                    recordsUseCases.getRecord(recordId)?.also { record ->
                        currentRecordId = record.id
                        _recordAmount.value = recordAmount.value.copy(
                            value = record.amount.removePrefix("-")
                        )
                        _typeIsExpenses.value = record.isExpenses
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditRecordEvent) {
        when (event) {
            is AddEditRecordEvent.ChangeRecordType -> {
                _typeIsExpenses.value = !typeIsExpenses.value
            }

            is AddEditRecordEvent.EnteredAmount -> {
                _recordAmount.value = recordAmount.value.copy(
                    value = event.value
                )
            }

            is AddEditRecordEvent.SaveRecord -> {
                viewModelScope.launch {
                    try {
                        recordsUseCases.addRecord(
                            Record(
                                timestamp = System.currentTimeMillis(),
                                amount = if (typeIsExpenses.value) "-${recordAmount.value.value}" else recordAmount.value.value,
                                isExpenses = typeIsExpenses.value,
                                id = currentRecordId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveRecord)
                    } catch (e: InvalidRecordException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save record"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data object SaveRecord : UiEvent()
    }
}