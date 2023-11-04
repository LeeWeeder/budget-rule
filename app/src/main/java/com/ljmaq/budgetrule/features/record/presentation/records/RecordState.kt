package com.ljmaq.budgetrule.features.record.presentation.records

import com.ljmaq.budgetrule.features.record.domain.model.Record

data class RecordState(
    val records: List<Record> = emptyList()
)

data class DialogState(
    val isEditRecordDialogOpen: Boolean = false,
    val isAddRecordDialogOpen: Boolean = false,
    val isAddExpenseRecordDialogOpen: Boolean = false
)