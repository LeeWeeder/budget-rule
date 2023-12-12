package com.ljmaq.budgetrule.features.record.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Income(
    @PrimaryKey val timestamp: Long,
    val amount: String
)