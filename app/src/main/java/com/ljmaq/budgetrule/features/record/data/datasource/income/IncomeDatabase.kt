package com.ljmaq.budgetrule.features.record.data.datasource.income

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ljmaq.budgetrule.features.record.domain.model.Income

@Database(
    entities = [Income::class],
    version = 1
)
abstract class IncomeDatabase: RoomDatabase() {
    abstract val incomeDao: IncomeDao

    companion object {
        const val DATABASE_NAME = "income_db"
    }
}