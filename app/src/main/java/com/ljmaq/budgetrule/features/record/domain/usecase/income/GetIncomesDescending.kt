package com.ljmaq.budgetrule.features.record.domain.usecase.income

import com.ljmaq.budgetrule.features.record.domain.model.Income
import com.ljmaq.budgetrule.features.record.domain.repository.IncomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetIncomesDescending(
    private val repository: IncomeRepository
) {
    operator fun invoke(): Flow<List<Income>> {
        return repository.getIncomes().map { incomes ->
            incomes.sortedByDescending { income ->
                income.timestamp
            }
        }
    }
}