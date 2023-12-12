package com.ljmaq.budgetrule.features.record.presentation.home.edit_record

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ljmaq.budgetrule.features.record.domain.model.Income
import com.ljmaq.budgetrule.features.record.domain.usecase.income.IncomesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditRecordViewModel @Inject constructor(
    private val recordsUseCases: IncomesUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _typeIsExpenses = mutableStateOf(
        false
    )
    val typeIsExpenses: State<Boolean> = _typeIsExpenses

    private val _recordAmount = mutableStateOf(
        EditRecordState.AmountTextFieldState()
    )

    val recordAmount: State<EditRecordState.AmountTextFieldState> = _recordAmount

    private val _tabState = mutableStateOf(
        EditRecordState.TabState()
    )
    val tabState: State<EditRecordState.TabState> = _tabState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentRecordId: Int? = null
    var currentRecord: Income? = null
    private var currentTimestamp: Long? = null

    init {
        savedStateHandle.get<Int>("recordId")?.let { recordId ->
            if (recordId != -1) {
                viewModelScope.launch {
//                    recordsUseCases.getIncomeById(recordId)?.also { record ->
//                        currentRecord = record
//                        currentRecordId = record.id
//                        currentTimestamp = record.timestamp
//                        _recordAmount.value = recordAmount.value.copy(
//                            value = record.amount.removePrefix("-")
//                        )
//                        _tabState.value = if (true) {
//                            tabState.value.copy(
//                                index = 1
//                            )
//                        } else {
//                            tabState.value.copy(
//                                index = 0
//                            )
//                        }
//                        _typeIsExpenses.value = true
//                    }
                }
            }
        }
    }

    fun onEvent(event: EditRecordEvent) {
        when (event) {
            is EditRecordEvent.ChangeRecordType -> {
                _typeIsExpenses.value = !typeIsExpenses.value
                _tabState.value = tabState.value.copy(
                    index = if (typeIsExpenses.value) {
                        1
                    } else {
                        0
                    }
                )
            }

            is EditRecordEvent.EnteredAmount -> {
                _recordAmount.value = recordAmount.value.copy(
                    value = event.value
                )
            }

            is EditRecordEvent.SaveRecord -> {
                viewModelScope.launch {
                    try {
                        currentTimestamp?.let {
                            Income(
                                timestamp = it,
                                amount = if (typeIsExpenses.value) "-${recordAmount.value.value}" else recordAmount.value.value,
//                                id = currentRecordId
                            )
                        }?.let {
                            recordsUseCases.insertIncome(
                                it
                            )
                        }
                        _eventFlow.emit(UiEvent.SaveRecord)
                    } catch (e: Exception) {
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
        data object ShowAmountWarningDialog : UiEvent()
    }
}