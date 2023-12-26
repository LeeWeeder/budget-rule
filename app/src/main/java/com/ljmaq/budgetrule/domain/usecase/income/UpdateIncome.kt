package com.ljmaq.budgetrule.domain.usecase.income

import com.ljmaq.budgetrule.domain.model.Income
import com.ljmaq.budgetrule.domain.repository.IncomeRepository

class UpdateIncome(
    private val repository: IncomeRepository
) {
    suspend operator fun invoke(income: Income) {
        repository.updateIncome(income)
    }
}