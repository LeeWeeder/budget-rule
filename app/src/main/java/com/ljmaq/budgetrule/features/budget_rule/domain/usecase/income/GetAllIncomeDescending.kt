package com.ljmaq.budgetrule.features.budget_rule.domain.usecase.income

import com.ljmaq.budgetrule.features.budget_rule.domain.model.Income
import com.ljmaq.budgetrule.features.budget_rule.domain.repository.IncomeRepository
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