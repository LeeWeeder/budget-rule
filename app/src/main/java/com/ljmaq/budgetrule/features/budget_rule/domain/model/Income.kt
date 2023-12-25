package com.ljmaq.budgetrule.features.budget_rule.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Income(
    @PrimaryKey val id: Int? = null,
    val timestamp: Long,
    val amount: Double
)