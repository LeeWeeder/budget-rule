package com.ljmaq.budgetrule.presentation.home

data class CreateRecordState(
    val selectedRecordType: com.ljmaq.budgetrule.presentation.home.RecordType = com.ljmaq.budgetrule.presentation.home.RecordType.Income,
    /*    val selectedPartition: Partition? = null,*/
    val amount: String = ""
)

sealed class RecordType {
    data object Income: com.ljmaq.budgetrule.presentation.home.RecordType()
    data object Expense: com.ljmaq.budgetrule.presentation.home.RecordType()
}