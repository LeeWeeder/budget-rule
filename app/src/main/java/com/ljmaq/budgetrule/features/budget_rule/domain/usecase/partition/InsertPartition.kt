package com.ljmaq.budgetrule.features.budget_rule.domain.usecase.partition

import com.ljmaq.budgetrule.features.budget_rule.domain.model.Partition
import com.ljmaq.budgetrule.features.budget_rule.domain.repository.PartitionRepository

class InsertPartition(
    private val repository: PartitionRepository
) {
    suspend operator fun invoke(partition: Partition) {
        repository.insertPartition(partition)
    }
}