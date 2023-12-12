package com.ljmaq.budgetrule.features.record.presentation.home

import com.ljmaq.budgetrule.features.record.domain.model.Income
import com.ljmaq.budgetrule.features.record.domain.model.Partition
import com.ljmaq.budgetrule.features.record.domain.model.partition.Investments
import com.ljmaq.budgetrule.features.record.domain.model.partition.Needs
import com.ljmaq.budgetrule.features.record.domain.model.partition.Savings
import com.ljmaq.budgetrule.features.record.domain.model.partition.Wants

data class IncomeState(
    val incomes: List<Income> = emptyList()
)

data class NeedsState(
    val needs: Partition.Needs = Partition.Needs(),
    val needsList: List<Needs> = emptyList()
)

data class WantsState(
    val wants: Partition.Wants = Partition.Wants(),
    val wantsList: List<Wants> = emptyList()
)

data class SavingsState(
    val savings: Partition.Savings = Partition.Savings(),
    val savingsList: List<Savings> = emptyList()
)

data class InvestmentsState(
    val investments: Partition.Investments = Partition.Investments(),
    val investmentsList: List<Investments> = emptyList()
)

data class DialogState(
    val deleteWarningDialog: Dialog = Dialog.DeleteWarningAlertDialog()
)

sealed class Dialog(
    open val state: Boolean
) {
    data class DeleteWarningAlertDialog(override val state: Boolean = false) : Dialog(state = state)
}