package com.ljmaq.budgetrule.features.record.domain.usecase

data class RecordsUseCases(
    val getRecords: GetRecords,
    val deleteRecord: DeleteRecord,
    val addRecord: AddRecord,
    val getRecord: GetRecord
)