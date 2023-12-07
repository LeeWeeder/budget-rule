package com.ljmaq.budgetrule.features.record.domain.usecase.partition.savings

import com.ljmaq.budgetrule.features.record.domain.model.partition.Savings
import com.ljmaq.budgetrule.features.record.domain.repository.partition.SavingsRepository

class GetSavingsById(
    private val repository: SavingsRepository
) {
    suspend operator fun invoke(id: Int): Savings? {
        return repository.getSavingsById(id)
    }
}