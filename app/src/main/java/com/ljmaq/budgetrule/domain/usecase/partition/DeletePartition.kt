package com.ljmaq.budgetrule.domain.usecase.partition

import com.ljmaq.budgetrule.domain.model.Partition
import com.ljmaq.budgetrule.domain.repository.PartitionRepository

class DeletePartition(
    private val repository: PartitionRepository
) {
    suspend operator fun invoke(partition: Partition) {
        repository.deletePartition(partition)
    }
}