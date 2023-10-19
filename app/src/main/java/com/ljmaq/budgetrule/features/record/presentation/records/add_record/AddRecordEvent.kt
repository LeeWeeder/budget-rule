package com.ljmaq.budgetrule.features.record.presentation.records.add_record

sealed class AddRecordEvent {
    data class ChangeRecordType(val index: Int): AddRecordEvent()
    data class EnteredAmount(val value: String): AddRecordEvent()
    data object SaveRecord: AddRecordEvent()
    data object BackSpace: AddRecordEvent()
    data object OverflowAmountTextField: AddRecordEvent()
    data class EnteredOperation(val operation: Char) : AddRecordEvent()
    data object Equals: AddRecordEvent()
    data object EnteredDecimal: AddRecordEvent()
}