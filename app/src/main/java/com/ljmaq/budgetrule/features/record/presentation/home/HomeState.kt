package com.ljmaq.budgetrule.features.record.presentation.home

import com.ljmaq.budgetrule.features.record.domain.model.Income
import com.ljmaq.budgetrule.features.record.domain.model.Partition

data class IncomeState(
    val incomes: List<Income> = emptyList()
)

data class PartitionState(
    val needs: Partition.Needs = Partition.Needs(),
    val wants: Partition.Wants = Partition.Wants(),
    val savings: Partition.Savings = Partition.Savings(),
    val investments: Partition.Investments = Partition.Investments(),
)

data class DialogState(
    val deleteWarningDialog: Dialog = Dialog.DeleteWarningAlertDialog()
)

sealed class Dialog(
    open val state: Boolean
) {
    data class DeleteWarningAlertDialog(override val state: Boolean = false) : Dialog(state = state)
}