package com.ljmaq.budgetrule.features.record.domain.usecase.partition.savings

import com.ljmaq.budgetrule.features.record.domain.model.partition.Savings
import com.ljmaq.budgetrule.features.record.domain.repository.partition.SavingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSavingsDescending(
    private val repository: SavingsRepository
) {
    operator fun invoke(): Flow<List<Savings>> {
        return repository.getSavings().map { savings ->
            savings.sortedByDescending {
                it.timestamp
            }
        }
    }
}