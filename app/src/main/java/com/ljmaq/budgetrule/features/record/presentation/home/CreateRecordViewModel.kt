package com.ljmaq.budgetrule.features.record.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ljmaq.budgetrule.features.record.domain.model.Partition
import com.ljmaq.budgetrule.features.record.domain.usecase.income.IncomesUseCases
import com.ljmaq.budgetrule.features.record.domain.util.isDigitsOnly
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateRecordViewModel @Inject constructor(
    private val incomesUseCases: IncomesUseCases,

    ) : ViewModel() {
    private val _state = mutableStateOf(CreateRecordState())
    val state: State<CreateRecordState> = _state

    private val _amount = mutableDoubleStateOf(0.0)
    val amount: State<Double> = _amount

    private var lastPartitionSelected: Partition? = null

    fun onEvent(event: CreateRecordEvent) {
        when (event) {
            is CreateRecordEvent.ChangeRecordType -> {
                _state.value = state.value.copy(
                    selectedRecordType = event.recordType,
                    selectedPartition = if (event.recordType == RecordType.Expense) {
                        if (lastPartitionSelected == null) Partition.Needs else lastPartitionSelected
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
        }
    }
}