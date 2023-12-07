package com.ljmaq.budgetrule.features.record.domain.usecase.partition.savings

import com.ljmaq.budgetrule.features.record.domain.model.partition.Savings
import com.ljmaq.budgetrule.features.record.domain.repository.partition.SavingsRepository
import kotlinx.coroutines.flow.Flow

class GetSavings(
    private val repository: SavingsRepository
) {
    operator fun invoke(): Flow<List<Savings>> {
        return repository.getSavings()
    }
}