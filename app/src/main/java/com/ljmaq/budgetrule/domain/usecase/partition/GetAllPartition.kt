package com.ljmaq.budgetrule.domain.usecase.partition

import com.ljmaq.budgetrule.domain.model.Partition
import com.ljmaq.budgetrule.domain.repository.PartitionRepository
import kotlinx.coroutines.flow.Flow

class GetAllPartition(
    private val repository: PartitionRepository
) {
    operator fun invoke(): Flow<List<Partition>> {
        return repository.getAllPartition()
    }
}