package com.ljmaq.budgetrule.features.record.data.datasource.partition.wants

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ljmaq.budgetrule.features.record.domain.model.partition.Wants
import kotlinx.coroutines.flow.Flow

@Dao
interface WantsDao {
    @Query("SELECT * FROM wants")
    fun getWants(): Flow<List<Wants>>

    @Query("SELECT * FROM wants WHERE timestamp = :id")
    suspend fun getWantsById(id: Long): Wants?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWants(wants: Wants)

    @Delete
    suspend fun deleteWants(wants: Wants)
}