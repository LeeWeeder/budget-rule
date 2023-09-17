package com.ljmaq.budgetrule.features.record.presentation.records.add_record

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
class AddRecordViewModel @Inject constructor(
    private val recordsUseCases: RecordsUseCases
) : ViewModel() {
    private val _typeIsExpenses = mutableStateOf(
        false
    )

    val typeIsExpenses: State<Boolean> = _typeIsExpenses

    private val _recordAmount = mutableStateOf(
        AddRecordState.AmountTextFieldState()
    )
    val recordAmount: State<AddRecordState.AmountTextFieldState> = _recordAmount

    private val _tabState = mutableStateOf(
        AddRecordState.TabState()
    )
    val tabState: State<AddRecordState.TabState> = _tabState

    private val _amountTextFieldFontSizeState = mutableStateOf(
        AddRecordState.AmountTextFieldFontSizeState()
    )
    val amountTextFieldFontSizeState: State<AddRecordState.AmountTextFieldFontSizeState> =
        _amountTextFieldFontSizeState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentRecordId: Int? = null

    fun onEvent(event: AddRecordEvent) {
        when (event) {
            is AddRecordEvent.ChangeRecordType -> {
                when (event.index) {
                    0 -> {
                        _typeIsExpenses.value = false
                        _tabState.value = tabState.value.copy(
                            index = 1
                        )
                    }

                    1 -> {
                        _typeIsExpenses.value = true
                        _tabState.value = tabState.value.copy(
                            index = 1
                        )
                    }
                }
            }

            is AddRecordEvent.EnteredAmount -> {
                _recordAmount.value = recordAmount.value.copy(
                    value = if ((recordAmount.value.value.contains('.') && event.value.endsWith('.')) || event.value.length > 28) {
                        event.value.dropLast(1)
                    } else if (recordAmount.value.value.startsWith('0')) {
                        if (event.value.contains('.'))
                            event.value
                        else if (event.value.isEmpty())
                            AddRecordState.AmountTextFieldState().value
                        else
                            event.value.removePrefix("0")
                    } else if (event.value.isEmpty()) {
                        AddRecordState.AmountTextFieldState().value
                    } else {
                        event.value
                    }
                )
            }

            is AddRecordEvent.SaveRecord -> {
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
                        _recordAmount.value = AddRecordState.AmountTextFieldState()
                        _amountTextFieldFontSizeState.value = AddRecordState.AmountTextFieldFontSizeState()
                    } catch (e: InvalidRecordException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save record"
                            )
                        )
                    }
                }
            }

            is AddRecordEvent.BackSpace -> {
                _amountTextFieldFontSizeState.value = amountTextFieldFontSizeState.value.copy(
                    value = if (amountTextFieldFontSizeState.value.value < 100 && recordAmount.value.value.length > 7)
                        amountTextFieldFontSizeState.value.value + 5
                    else
                        100
                )
            }

            is AddRecordEvent.OverflowAmountTextField -> {
                _amountTextFieldFontSizeState.value = amountTextFieldFontSizeState.value.copy(
                    value = amountTextFieldFontSizeState.value.value - 5
                )
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data object SaveRecord : UiEvent()
    }
}