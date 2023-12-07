package com.ljmaq.budgetrule.features.record.presentation.home.edit_record

sealed class EditRecordState {
    data class AmountTextFieldState(
        val value: String = ""
    ) : EditRecordState()

    data class TabState(
        val index: Int = 0
    ) : EditRecordState()
}