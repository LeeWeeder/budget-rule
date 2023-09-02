package com.ljmaq.budgetrule.features.record.domain.usecase

import com.ljmaq.budgetrule.features.record.domain.model.Record
import com.ljmaq.budgetrule.features.record.domain.repository.RecordRepository

class DeleteRecord(
    private val repository: RecordRepository
) {
    suspend operator fun invoke(record: Record) {
        repository.deleteRecord(record)
    }
}