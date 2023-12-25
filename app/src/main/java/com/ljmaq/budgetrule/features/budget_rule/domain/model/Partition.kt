package com.ljmaq.budgetrule.features.budget_rule.domain.model

import androidx.annotation.FloatRange
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Partition(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val amount: Double,
    @FloatRange(from = 0.0, to = 1.0) val sharePercent: Float,
)