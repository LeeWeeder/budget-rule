package com.ljmaq.budgetrule.features.record.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Record(
    val timestamp: Long,
    val amount: String,
    val isExpenses: Boolean,
    @PrimaryKey val id: Int? = null
)

class InvalidRecordException(message: String) : Exception(message)