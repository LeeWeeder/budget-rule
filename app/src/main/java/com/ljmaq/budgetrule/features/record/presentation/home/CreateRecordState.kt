package com.ljmaq.budgetrule.features.record.presentation.home

import com.ljmaq.budgetrule.features.record.domain.model.Partition

data class CreateRecordState(
    val selectedRecordType: RecordType = RecordType.Income,
    val selectedPartition: Partition? = null,
    val amount: String = ""
)

sealed class RecordType {
    data object Income: RecordType()
    data object Expense: RecordType()
}