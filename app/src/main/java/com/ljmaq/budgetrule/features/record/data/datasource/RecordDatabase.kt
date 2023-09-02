package com.ljmaq.budgetrule.features.record.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ljmaq.budgetrule.features.record.domain.model.Record

@Database(
    entities = [Record::class],
    version = 1
)
abstract class RecordDatabase: RoomDatabase() {
    abstract val recordDao: RecordDao

    companion object {
        const val DATABASE_NAME = "records_db"
    }
}