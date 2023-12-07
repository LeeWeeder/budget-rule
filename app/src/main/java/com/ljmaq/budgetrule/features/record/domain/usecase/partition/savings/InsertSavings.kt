package com.ljmaq.budgetrule.features.record.domain.usecase.partition.savings

import com.ljmaq.budgetrule.features.record.domain.model.partition.Savings
import com.ljmaq.budgetrule.features.record.domain.repository.partition.SavingsRepository

class InsertSavings(
    private val repository: SavingsRepository
) {
    suspend operator fun invoke(savings: Savings) {
        repository.insertSavings(savings)
    }
}
