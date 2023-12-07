package com.ljmaq.budgetrule.features.record.data.datasource.partition.savings

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ljmaq.budgetrule.features.record.domain.model.partition.Savings

@Database(
    entities = [Savings::class],
    version = 1
)
abstract class SavingsDatabase: RoomDatabase() {
    abstract val savingsDao: SavingsDao

    companion object {
        const val DATABASE_NAME = "savings_db"
    }
}