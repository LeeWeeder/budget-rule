package com.ljmaq.budgetrule.presentation.home

sealed class CreateRecordEvent {
    data class ChangeRecordType(val recordType: RecordType): CreateRecordEvent()
//    data class ChangePartition(val partition: Partition): CreateRecordEvent()
    data class UpdateAmount(val amount: String): CreateRecordEvent()
    data object ResetState : CreateRecordEvent()
    data object InsertRecord : CreateRecordEvent()
}