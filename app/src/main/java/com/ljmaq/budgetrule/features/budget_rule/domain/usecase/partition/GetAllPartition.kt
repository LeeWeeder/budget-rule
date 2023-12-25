package com.ljmaq.budgetrule.features.budget_rule.domain.usecase.partition

import com.ljmaq.budgetrule.features.budget_rule.domain.model.Partition
import com.ljmaq.budgetrule.features.budget_rule.domain.repository.PartitionRepository
import kotlinx.coroutines.flow.Flow

class GetAllPartition(
    private val repository: PartitionRepository
) {
    operator fun invoke(): Flow<List<Partition>> {
        return repository.getAllPartition()
    }
}