package com.ljmaq.budgetrule.features.record.domain.repository

import com.ljmaq.budgetrule.features.record.domain.model.Record
import kotlinx.coroutines.flow.Flow

interface RecordRepository {
    fun getRecords(): Flow<List<Record>>

    suspend fun getRecordById(id: Int): Record?

    suspend fun insertRecord(record: Record)

    suspend fun deleteRecord(record: Record)
}