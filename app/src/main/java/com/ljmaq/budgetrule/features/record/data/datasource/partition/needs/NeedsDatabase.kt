package com.ljmaq.budgetrule.features.record.data.datasource.partition.needs

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ljmaq.budgetrule.features.record.domain.model.partition.Needs

@Database(
    entities = [Needs::class],
    version = 1
)
abstract class NeedsDatabase: RoomDatabase() {
    abstract val needsDao: NeedsDao

    companion object {
        const val DATABASE_NAME = "needs_db"
    }
}