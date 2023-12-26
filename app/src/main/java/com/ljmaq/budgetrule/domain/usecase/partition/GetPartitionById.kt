package com.ljmaq.budgetrule.domain.usecase.partition

import com.ljmaq.budgetrule.domain.model.Partition
import com.ljmaq.budgetrule.domain.repository.PartitionRepository

class GetPartitionById(
    private val repository: PartitionRepository
) {
    suspend operator fun invoke(id: Int?): Partition? {
        return repository.getPartitionById(id)
    }
}