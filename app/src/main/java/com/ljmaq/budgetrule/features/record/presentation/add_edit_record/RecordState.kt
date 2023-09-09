package com.ljmaq.budgetrule.features.record.presentation.add_edit_record

sealed class RecordState {
    data class AmountTextFieldState(
        val value: String = ""
    ) : RecordState()

    data class TabState(
        val index: Int = 0
    ) : RecordState()
}