package com.ljmaq.budgetrule.features.record.data.datasource.partition.needs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ljmaq.budgetrule.features.record.domain.model.partition.Needs
import kotlinx.coroutines.flow.Flow

@Dao
interface NeedsDao {
    @Query("SELECT * FROM needs")
    fun getNeeds(): Flow<List<Needs>>

    @Query("SELECT * FROM needs WHERE timestamp = :id")
    suspend fun getNeedsById(id: Long): Needs?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNeeds(needs: Needs)

    @Delete
    suspend fun deleteNeeds(needs: Needs)
}