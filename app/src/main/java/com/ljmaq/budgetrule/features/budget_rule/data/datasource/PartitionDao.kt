package com.ljmaq.budgetrule.features.budget_rule.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ljmaq.budgetrule.features.budget_rule.domain.model.Partition
import kotlinx.coroutines.flow.Flow

@Dao
interface PartitionDao {
    @Query("SELECT * FROM partition")
    fun getAllPartition(): Flow<List<Partition>>

    @Query("SELECT * FROM partition WHERE id = :id")
    suspend fun getPartitionById(id: Int?): Partition?

    @Insert
    suspend fun insertPartition(partition: Partition)

    @Update
    suspend fun updatePartition(partition: Partition)
    @Delete
    suspend fun deletePartition(partition: Partition)
}