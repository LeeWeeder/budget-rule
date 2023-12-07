package com.ljmaq.budgetrule.features.record.data.datasource.partition.needs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.ljmaq.budgetrule.features.record.domain.model.partition.Needs

@Dao
interface NeedsDao {
    @Query("SELECT * FROM needs")
    fun getNeeds(): Flow<List<Needs>>

    @Query("SELECT * FROM needs WHERE id = :id")
    suspend fun getNeedsById(id: Int): Needs?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNeeds(needs: Needs)

    @Delete
    suspend fun deleteNeeds(needs: Needs)
}