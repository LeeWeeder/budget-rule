package com.ljmaq.budgetrule.features.record.presentation.records

import com.ljmaq.budgetrule.features.record.domain.model.Record

data class RecordState(
    val records: List<Record> = emptyList()
)