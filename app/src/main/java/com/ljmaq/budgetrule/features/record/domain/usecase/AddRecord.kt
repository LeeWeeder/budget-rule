package com.ljmaq.budgetrule.features.record.domain.usecase

import com.ljmaq.budgetrule.features.record.domain.model.InvalidRecordException
import com.ljmaq.budgetrule.features.record.domain.model.Record
import com.ljmaq.budgetrule.features.record.domain.repository.RecordRepository
import com.ljmaq.budgetrule.features.record.domain.util.isDigitsOnly

class AddRecord(
    private val repository: RecordRepository
) {
    @Throws(InvalidRecordException::class)
    suspend operator fun invoke(record: Record) {
        if (record.amount.isBlank()) {
            throw InvalidRecordException("The amount can't be empty.")
        }

        if (!record.amount.isDigitsOnly()) {
            throw InvalidRecordException("Please enter a valid amount.")
        }
        repository.insertRecord(record)
    }
}