package com.ljmaq.budgetrule.features.record.presentation.home

import com.ljmaq.budgetrule.features.record.domain.model.Income

data class IncomeState(
    val incomes: List<Income> = emptyList()
)

data class DialogState(
    val deleteWarningDialog: Dialog = Dialog.DeleteWarningAlertDialog()
)

sealed class Dialog(
    open val state: Boolean
) {
    data class DeleteWarningAlertDialog(override val state: Boolean = false): Dialog(state = state)
}