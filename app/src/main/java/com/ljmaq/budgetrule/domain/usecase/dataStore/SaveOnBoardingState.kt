package com.ljmaq.budgetrule.domain.usecase.dataStore

import com.ljmaq.budgetrule.domain.repository.DataStoreRepository

class SaveOnBoardingState(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke(showOnBoarding: Boolean) {
        repository.saveOnBoardingState(showOnBoarding)
    }
}