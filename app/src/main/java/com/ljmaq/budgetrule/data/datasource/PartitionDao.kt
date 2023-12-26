package com.ljmaq.budgetrule.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ljmaq.budgetrule.domain.model.Partition
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