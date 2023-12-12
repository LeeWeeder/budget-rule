package com.ljmaq.budgetrule.features.record.domain.model.partition

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Savings(
    @PrimaryKey val timestamp: Long,
    val amount: String
)