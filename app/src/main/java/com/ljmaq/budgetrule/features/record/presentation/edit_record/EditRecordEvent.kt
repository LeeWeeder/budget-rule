package com.ljmaq.budgetrule.features.record.presentation.edit_record

sealed class EditRecordEvent {
    data object ChangeRecordType: EditRecordEvent()
    data class EnteredAmount(val value: String): EditRecordEvent()
    data object SaveRecord: EditRecordEvent()
}