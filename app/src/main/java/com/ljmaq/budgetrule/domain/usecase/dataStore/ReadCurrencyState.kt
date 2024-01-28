package com.ljmaq.budgetrule.domain.usecase.dataStore

import com.ljmaq.budgetrule.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class ReadCurrencyState(
    private val repository: DataStoreRepository
) {
    operator fun invoke(): Flow<String> {
        return repository.readCurrencyState()
    }
}