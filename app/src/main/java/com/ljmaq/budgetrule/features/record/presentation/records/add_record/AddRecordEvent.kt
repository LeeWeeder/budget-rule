package com.ljmaq.budgetrule.features.record.presentation.records.add_record

sealed class AddRecordEvent {
    data object ChangeRecordType: AddRecordEvent()
    data class EnteredAmount(val value: String): AddRecordEvent()
    data object SaveRecord: AddRecordEvent()
}