package com.ljmaq.budgetrule.features.record.presentation.home.add_record

sealed class AddRecordState {
    data class AmountTextFieldState(
        val value: String = "0"
    ) : AddRecordState()

    data class TabState(
        val index: Int = 0
    ) : AddRecordState()

    data class AmountTextFieldFontSizeState(
        val value: Int = 100
    ) : AddRecordState()
}