package com.ljmaq.budgetrule.features.record.presentation.records.add_record

sealed class AddRecordState {
    data class AmountTextFieldState(
        val value: String = ""
    ) : AddRecordState()

    data class TabState(
        val index: Int = 0
    ) : AddRecordState()
}