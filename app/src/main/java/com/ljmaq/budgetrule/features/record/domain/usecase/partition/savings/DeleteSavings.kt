package com.ljmaq.budgetrule.features.record.domain.usecase.partition.savings

import com.ljmaq.budgetrule.features.record.domain.model.partition.Savings
import com.ljmaq.budgetrule.features.record.domain.repository.partition.SavingsRepository

class DeleteSavings(
    private val repository: SavingsRepository
) {
    suspend operator fun invoke(savings: Savings) {
        repository.deleteSavings(savings)
    }
}