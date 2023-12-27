package com.ljmaq.budgetrule.domain.usecase.dataStore

import com.ljmaq.budgetrule.domain.repository.DataStoreRepository

class SaveBalanceState(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke(balance: Double) {
        repository.saveBalanceState(balance)
    }
}