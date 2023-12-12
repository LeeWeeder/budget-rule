package com.ljmaq.budgetrule.features.record.domain.model.partition

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Investments(
    @PrimaryKey val timestamp: Long,
    val amount: String
)