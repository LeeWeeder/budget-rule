package com.ljmaq.budgetrule.domain.usecase.dataStore

import com.ljmaq.budgetrule.domain.repository.DataStoreRepository

class SaveCurrencyState(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke(currencyCode: String) {
        repository.saveCurrencyState(currencyCode)
    }
}