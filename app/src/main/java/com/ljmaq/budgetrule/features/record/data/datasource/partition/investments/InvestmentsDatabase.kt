package com.ljmaq.budgetrule.features.record.data.datasource.partition.investments

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ljmaq.budgetrule.features.record.domain.model.partition.Investments

@Database(
    entities = [Investments::class],
    version = 1
)
abstract class InvestmentsDatabase: RoomDatabase() {
    abstract val investmentsDao: InvestmentsDao

    companion object {
        const val DATABASE_NAME = "investments_db"
    }
}