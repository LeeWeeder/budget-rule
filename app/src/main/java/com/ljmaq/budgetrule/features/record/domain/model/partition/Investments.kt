package com.ljmaq.budgetrule.features.record.domain.model.partition

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Investments(
    val timestamp: Long,
    val amount: String,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val partitionValue = 0.1
    }
}