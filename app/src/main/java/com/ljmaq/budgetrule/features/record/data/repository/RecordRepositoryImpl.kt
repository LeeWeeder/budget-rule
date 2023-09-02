package com.ljmaq.budgetrule.features.record.data.repository

import com.ljmaq.budgetrule.features.record.data.datasource.RecordDao
import com.ljmaq.budgetrule.features.record.domain.model.Record
import com.ljmaq.budgetrule.features.record.domain.repository.RecordRepository
import kotlinx.coroutines.flow.Flow

class RecordRepositoryImpl(
    private val dao: RecordDao
) : RecordRepository {
    override fun getRecords(): Flow<List<Record>> {
        return dao.getRecords()
    }

    override suspend fun getRecordById(id: Int): Record? {
        return dao.getRecordById(id)
    }

    override suspend fun insertRecord(record: Record) {
        dao.insertRecord(record)
    }

    override suspend fun deleteRecord(record: Record) {
        dao.deleteRecord(record)
    }
}