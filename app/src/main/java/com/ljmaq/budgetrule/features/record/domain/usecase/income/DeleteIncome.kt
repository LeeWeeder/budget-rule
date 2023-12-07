package com.ljmaq.budgetrule.features.record.domain.usecase.income

import com.ljmaq.budgetrule.features.record.domain.model.Income
import com.ljmaq.budgetrule.features.record.domain.repository.IncomeRepository

class DeleteIncome(
    private val repository: IncomeRepository
) {
    suspend operator fun invoke(income: Income) {
        repository.deleteIncome(income)
    }
}