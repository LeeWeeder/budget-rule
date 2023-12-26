package com.ljmaq.budgetrule.features.budget_rule.domain.usecase.income

import com.ljmaq.budgetrule.features.budget_rule.domain.model.Income
import com.ljmaq.budgetrule.features.budget_rule.domain.repository.IncomeRepository

class InsertIncome(
    private val repository: IncomeRepository
) {
    suspend operator fun invoke(income: Income) {
        repository.insertIncome(income)
    }
}