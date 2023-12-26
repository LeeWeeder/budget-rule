package com.ljmaq.budgetrule.features.onboarding.domain.usecase

import com.ljmaq.budgetrule.features.onboarding.domain.repository.OnBoardingRepository
import kotlinx.coroutines.flow.Flow

class ReadOnBoardingState(
    private val repository: OnBoardingRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return repository.readOnBoardingState()
    }
}