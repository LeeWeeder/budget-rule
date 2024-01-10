package com.ljmaq.budgetrule.domain.usecase.dataStore

import com.ljmaq.budgetrule.domain.model.Partition
import com.ljmaq.budgetrule.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class ReadExcessPartitionState(
    private val repository: DataStoreRepository
) {
    operator fun invoke(): Flow<Partition> {
        return repository.readExcessPartitionState()
    }
}