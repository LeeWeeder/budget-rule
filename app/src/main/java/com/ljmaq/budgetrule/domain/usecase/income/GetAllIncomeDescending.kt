package com.ljmaq.budgetrule.domain.usecase.income

import com.ljmaq.budgetrule.domain.model.Income
import com.ljmaq.budgetrule.domain.repository.IncomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllIncomeDescending(
    private val repository: IncomeRepository
) {
    operator fun invoke(): Flow<List<Income>> {
        return repository.getAllIncome().map { incomes ->
            incomes.sortedByDescending { income ->
                income.timestamp
            }
        }
    }
}