package com.ljmaq.budgetrule.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense(
    @PrimaryKey val id: Int,
    val timestamp: Long,
    val amount: Double,
    val partition: String
)