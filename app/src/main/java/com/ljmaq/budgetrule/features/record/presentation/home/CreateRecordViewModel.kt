package com.ljmaq.budgetrule.features.record.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ljmaq.budgetrule.features.record.domain.model.Income
import com.ljmaq.budgetrule.features.record.domain.model.Partition
import com.ljmaq.budgetrule.features.record.domain.model.partition.Investments
import com.ljmaq.budgetrule.features.record.domain.model.partition.Needs
import com.ljmaq.budgetrule.features.record.domain.model.partition.Savings
import com.ljmaq.budgetrule.features.record.domain.model.partition.Wants
import com.ljmaq.budgetrule.features.record.domain.usecase.income.IncomesUseCases
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.investments.InvestmentsUseCases
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.needs.NeedsUseCases
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.savings.SavingsUseCases
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.wants.WantsUseCases
import com.ljmaq.budgetrule.features.record.domain.util.isDigitsOnly
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateRecordViewModel @Inject constructor(
    private val incomesUseCases: IncomesUseCases,
    private val needsUseCases: NeedsUseCases,
    private val wantsUseCases: WantsUseCases,
    private val investmentsUseCases: InvestmentsUseCases,
    private val savingsUseCases: SavingsUseCases
) : ViewModel() {
    private val _state = mutableStateOf(CreateRecordState())
    val state: State<CreateRecordState> = _state

    private var lastPartitionSelected: Partition? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: CreateRecordEvent) {
        when (event) {
            is CreateRecordEvent.ChangeRecordType -> {
                _state.value = state.value.copy(
                    selectedRecordType = event.recordType,
                    selectedPartition = if (event.recordType == RecordType.Expense) {
                        if (lastPartitionSelected == null) Partition.Needs() else lastPartitionSelected
                    } else {
                        lastPartitionSelected = state.value.selectedPartition
                        null
                    }
                )
            }

            CreateRecordEvent.ResetState -> {
                _state.value = CreateRecordState()
            }

            is CreateRecordEvent.ChangePartition -> {
                _state.value = state.value.copy(selectedPartition = event.partition)
            }

            is CreateRecordEvent.UpdateAmount -> {
                _state.value = state.value.copy(
                    amount = if (event.amount == ".") {
                        "0" + event.amount
                    } else if (event.amount.isDigitsOnly()) {
                        event.amount
                    } else {
                        state.value.amount
                    }
                )
            }

            CreateRecordEvent.InsertRecord -> {
                viewModelScope.launch {
                    when (state.value.selectedRecordType) {
                        RecordType.Expense -> {
                            when (state.value.selectedPartition!!) {
                                is Partition.Investments -> {
                                    try {
                                        val timestamp = System.currentTimeMillis()
                                        val amount = state.value.amount
                                        investmentsUseCases.insertInvestments(
                                            Investments(
                                                timestamp = timestamp,
                                                amount = (amount.toDouble() * -1).toString()
                                            )
                                        )
                                        _eventFlow.emit(UiEvent.SaveRecord)
                                    } catch (e: Exception) {
                                        _eventFlow.emit(
                                            UiEvent.ShowSnackbar(
                                                e.message ?: "Couldn't save record"
                                            )
                                        )
                                    }
                                }

                                is Partition.Needs -> {
                                    try {
                                        val timestamp = System.currentTimeMillis()
                                        val amount = state.value.amount
                                        needsUseCases.insertNeeds(
                                            Needs(
                                                timestamp = timestamp,
                                                amount = (amount.toDouble() * -1).toString()
                                            )
                                        )
                                        _eventFlow.emit(UiEvent.SaveRecord)
                                    } catch (e: Exception) {
                                        _eventFlow.emit(
                                            UiEvent.ShowSnackbar(
                                                e.message ?: "Couldn't save record"
                                            )
                                        )
                                    }
                                }

                                is Partition.Savings -> {
                                    try {
                                        val timestamp = System.currentTimeMillis()
                                        val amount = state.value.amount
                                        savingsUseCases.insertSavings(
                                            Savings(
                                                timestamp = timestamp,
                                                amount = (amount.toDouble() * -1).toString()
                                            )
                                        )
                                        _eventFlow.emit(UiEvent.SaveRecord)
                                    } catch (e: Exception) {
                                        _eventFlow.emit(
                                            UiEvent.ShowSnackbar(
                                                e.message ?: "Couldn't save record"
                                            )
                                        )
                                    }
                                }

                                is Partition.Wants -> {
                                    try {
                                        val timestamp = System.currentTimeMillis()
                                        val amount = state.value.amount
                                        wantsUseCases.insertWants(
                                            Wants(
                                                timestamp = timestamp,
                                                amount = (amount.toDouble() * -1).toString()
                                            )
                                        )
                                        _eventFlow.emit(UiEvent.SaveRecord)
                                    } catch (e: Exception) {
                                        _eventFlow.emit(
                                            UiEvent.ShowSnackbar(
                                                e.message ?: "Couldn't save record"
                                            )
                                        )
                                    }
                                }
                            }
                        }

                        RecordType.Income -> {
                            try {
                                val timestamp = System.currentTimeMillis()
                                val amount = state.value.amount
                                incomesUseCases.insertIncome(
                                    Income(
                                        timestamp = timestamp,
                                        amount = amount
                                    )
                                )
                                _eventFlow.emit(UiEvent.SaveRecord)
                                needsUseCases.insertNeeds(
                                    Needs(
                                        timestamp = timestamp,
                                        amount = (Partition.Needs().partitionValue * amount.toDouble()).toString()
                                    )
                                )
                                wantsUseCases.insertWants(
                                    Wants(
                                        timestamp = timestamp,
                                        amount = (Partition.Wants().partitionValue * amount.toDouble()).toString()
                                    )
                                )
                                savingsUseCases.insertSavings(
                                    Savings(
                                        timestamp = timestamp,
                                        amount = (Partition.Savings().partitionValue * amount.toDouble()).toString()
                                    )
                                )
                                investmentsUseCases.insertInvestments(
                                    Investments(
                                        timestamp = timestamp,
                                        amount = (Partition.Investments().partitionValue * amount.toDouble()).toString()
                                    )
                                )
                            } catch (e: Exception) {
                                _eventFlow.emit(
                                    UiEvent.ShowSnackbar(e.message ?: "Couldn't save record")
                                )
                            }

                        }
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