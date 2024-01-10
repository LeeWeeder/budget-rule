package com.ljmaq.budgetrule.domain.usecase.dataStore

import com.ljmaq.budgetrule.domain.model.Partition
import com.ljmaq.budgetrule.domain.repository.DataStoreRepository

class SaveExcessPartitionState(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke(partition: Partition) {
        repository.saveExcessPartitionState(partition)
    }
}