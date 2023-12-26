package com.ljmaq.budgetrule.domain.usecase.income

import com.ljmaq.budgetrule.domain.model.Income
import com.ljmaq.budgetrule.domain.repository.IncomeRepository

class GetIncomeById(
    private val repository: IncomeRepository
) {
    suspend operator fun invoke(id: Int): Income? {
        return repository.getIncomeById(id)
    }
}