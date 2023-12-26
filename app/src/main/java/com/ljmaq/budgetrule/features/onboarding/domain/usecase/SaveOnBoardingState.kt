package com.ljmaq.budgetrule.features.onboarding.domain.usecase

import com.ljmaq.budgetrule.features.onboarding.domain.repository.OnBoardingRepository

class SaveOnBoardingState(
    private val repository: OnBoardingRepository
) {
    suspend operator fun invoke(completed: Boolean) {
        repository.saveOnBoardingState(completed)
    }
}