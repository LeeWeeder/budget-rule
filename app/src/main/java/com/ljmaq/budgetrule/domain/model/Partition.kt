package com.ljmaq.budgetrule.domain.model

import androidx.annotation.FloatRange
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Partition(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val amount: Double,
    val avatar: String? = null,
    val color: Long? = null,
    @FloatRange(from = 0.0, to = 1.0) val sharePercent: Float,
)