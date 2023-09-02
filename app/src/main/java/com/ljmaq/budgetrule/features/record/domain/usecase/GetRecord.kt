package com.ljmaq.budgetrule.features.record.domain.usecase

import com.ljmaq.budgetrule.features.record.domain.model.Record
import com.ljmaq.budgetrule.features.record.domain.repository.RecordRepository

class GetRecord(
    private val repository: RecordRepository
) {
    suspend operator fun invoke(id: Int): Record? {
        return repository.getRecordById(id)
    }
}