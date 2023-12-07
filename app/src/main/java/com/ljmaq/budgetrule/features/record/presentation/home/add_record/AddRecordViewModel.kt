package com.ljmaq.budgetrule.features.record.presentation.home.add_record

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
class AddRecordViewModel @Inject constructor(
    private val recordsUseCases: IncomesUseCases
) : ViewModel() {
    private val _typeIsExpenses = mutableStateOf(
        false
    )

    val typeIsExpenses: State<Boolean> = _typeIsExpenses

    private val _recordAmount = mutableStateOf(
        AddRecordState.AmountTextFieldState()
    )
    val amount: State<AddRecordState.AmountTextFieldState> = _recordAmount

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
                _recordAmount.value = amount.value.copy(
                    value = if (amount.value.value == "0") event.value else amount.value.value + event.value
                )
            }

            is AddRecordEvent.SaveRecord -> {
                viewModelScope.launch {
                    try {
                        recordsUseCases.insertIncome(
                            Income(
                                timestamp = System.currentTimeMillis(),
                                amount = if (typeIsExpenses.value) "-${amount.value.value}" else amount.value.value,
                                id = currentRecordId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveRecord)
                        _recordAmount.value = AddRecordState.AmountTextFieldState()
                        _amountTextFieldFontSizeState.value =
                            AddRecordState.AmountTextFieldFontSizeState()
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(e.message ?: "Couldn't save record")
                        )
                    }
                }
            }

            is AddRecordEvent.BackSpace -> {
                _amountTextFieldFontSizeState.value = amountTextFieldFontSizeState.value.copy(
                    value = if (amountTextFieldFontSizeState.value.value < 100 && amount.value.value.length > 7)
                        amountTextFieldFontSizeState.value.value + 5
                    else
                        AddRecordState.AmountTextFieldFontSizeState().value
                )
                _recordAmount.value = amount.value.copy(
                    value = if (amount.value.value.length > 1)
                        amount.value.value.dropLast(1)
                    else
                        AddRecordState.AmountTextFieldState().value
                )
            }

            is AddRecordEvent.OverflowAmountTextField -> {
                _amountTextFieldFontSizeState.value = amountTextFieldFontSizeState.value.copy(
                    value = amountTextFieldFontSizeState.value.value - 5
                )
            }

            is AddRecordEvent.EnteredDecimal -> {
                if (!amount.value.value.contains('.')) {
                    _recordAmount.value = amount.value.copy(
                        value = amount.value.value + "."
                    )
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