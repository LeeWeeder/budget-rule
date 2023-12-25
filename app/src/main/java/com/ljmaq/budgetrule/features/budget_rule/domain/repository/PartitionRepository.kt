package com.ljmaq.budgetrule.features.budget_rule.domain.repository

import com.ljmaq.budgetrule.features.budget_rule.domain.model.Partition
import kotlinx.coroutines.flow.Flow

interface PartitionRepository {
    fun getAllPartition(): Flow<List<Partition>>
    suspend fun getPartitionById(id: Int?): Partition?
    suspend fun insertPartition(partition: Partition)
    suspend fun deletePartition(partition: Partition)
    suspend fun updatePartition(partition: Partition)
}