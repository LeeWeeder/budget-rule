package com.ljmaq.budgetrule.features.record.domain.usecase

import com.ljmaq.budgetrule.features.record.domain.model.Record
import com.ljmaq.budgetrule.features.record.domain.repository.RecordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRecords(
    private val repository: RecordRepository
) {
    operator fun invoke(): Flow<List<Record>> {
        return repository.getRecords().map { records ->
            records.sortedByDescending { record ->
                record.timestamp
            }
        }
    }
}