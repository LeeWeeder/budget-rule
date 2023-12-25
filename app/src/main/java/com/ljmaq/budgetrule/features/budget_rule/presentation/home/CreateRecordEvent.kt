package com.ljmaq.budgetrule.features.budget_rule.presentation.home

sealed class CreateRecordEvent {
    data class ChangeRecordType(val recordType: com.ljmaq.budgetrule.features.budget_rule.presentation.home.RecordType): com.ljmaq.budgetrule.features.budget_rule.presentation.home.CreateRecordEvent()
//    data class ChangePartition(val partition: Partition): com.ljmaq.budgetrule.features.budget_rule.presentation.home.CreateRecordEvent()
    data class UpdateAmount(val amount: String): com.ljmaq.budgetrule.features.budget_rule.presentation.home.CreateRecordEvent()
    data object ResetState : com.ljmaq.budgetrule.features.budget_rule.presentation.home.CreateRecordEvent()
    data object InsertRecord : com.ljmaq.budgetrule.features.budget_rule.presentation.home.CreateRecordEvent()
}