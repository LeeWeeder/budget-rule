package com.ljmaq.budgetrule.features.record.domain.model.partition

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Wants(
    val timestamp: Long,
    val amount: String,
    @PrimaryKey val id: Int? = null
)