package com.ljmaq.budgetrule.features.record.domain.usecase

import com.ljmaq.budgetrule.features.record.domain.model.InvalidRecordException
import com.ljmaq.budgetrule.features.record.domain.model.Record
import com.ljmaq.budgetrule.features.record.domain.repository.RecordRepository

class AddRecord(
    private val repository: RecordRepository
) {
    @Throws(InvalidRecordException::class)
    suspend operator fun invoke(record: Record) {
        if (record.amount == "0" || record.amount == "-0") {
            throw InvalidRecordException("The amount can't be empty")
        }

        repository.insertRecord(record)
    }
}