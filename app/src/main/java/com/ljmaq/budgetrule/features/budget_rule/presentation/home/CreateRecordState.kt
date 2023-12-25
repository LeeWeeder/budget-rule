package com.ljmaq.budgetrule.features.budget_rule.presentation.home

data class CreateRecordState(
    val selectedRecordType: RecordType = RecordType.Income,
/*    val selectedPartition: Partition? = null,*/
    val amount: String = ""
)

sealed class RecordType {
    data object Income: RecordType()
    data object Expense: RecordType()
}