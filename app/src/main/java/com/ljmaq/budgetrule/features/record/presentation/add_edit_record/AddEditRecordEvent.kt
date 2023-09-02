package com.ljmaq.budgetrule.features.record.presentation.add_edit_record

sealed class AddEditRecordEvent {
    data object ChangeRecordType: AddEditRecordEvent()
    data class EnteredAmount(val value: String): AddEditRecordEvent()
    data object SaveRecord: AddEditRecordEvent()
}