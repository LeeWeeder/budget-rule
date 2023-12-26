package com.ljmaq.budgetrule.data.repository

import com.ljmaq.budgetrule.data.datasource.PartitionDao
import com.ljmaq.budgetrule.domain.model.Partition
import com.ljmaq.budgetrule.domain.repository.PartitionRepository
import kotlinx.coroutines.flow.Flow

class PartitionRepositoryImpl(
    private val dao: PartitionDao
): PartitionRepository {
    override fun getAllPartition(): Flow<List<Partition>> {
        return dao.getAllPartition()
    }

    override suspend fun getPartitionById(id: Int?): Partition? {
        return dao.getPartitionById(id)
    }

    override suspend fun insertPartition(partition: Partition) {
        dao.insertPartition(partition)
    }

    override suspend fun deletePartition(partition: Partition) {
        dao.deletePartition(partition)
    }

    override suspend fun updatePartition(partition: Partition) {
        dao.updatePartition(partition)
    }
}