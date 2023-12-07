package com.ljmaq.budgetrule.features.record.data.datasource.partition.wants

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ljmaq.budgetrule.features.record.domain.model.partition.Needs
import com.ljmaq.budgetrule.features.record.domain.model.partition.Wants

@Database(
    entities = [Wants::class],
    version = 1
)
abstract class WantsDatabase: RoomDatabase() {
    abstract val wantsDao: WantsDao

    companion object {
        const val DATABASE_NAME = "wants_db"
    }
}