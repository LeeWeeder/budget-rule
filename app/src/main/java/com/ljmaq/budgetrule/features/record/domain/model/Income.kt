package com.ljmaq.budgetrule.features.record.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Income(
    val timestamp: Long,
    val amount: String,
    @PrimaryKey val id: Int? = null
)