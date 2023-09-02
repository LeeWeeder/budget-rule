package com.ljmaq.budgetrule.features.record.domain.usecase

import com.ljmaq.budgetrule.features.record.domain.model.Record
import com.ljmaq.budgetrule.features.record.domain.repository.RecordRepository
import kotlinx.coroutines.flow.Flow

class GetRecords(
    private val repository: RecordRepository
) {
    operator fun invoke(): Flow<List<Record>> {
        return repository.getRecords()
    }
}