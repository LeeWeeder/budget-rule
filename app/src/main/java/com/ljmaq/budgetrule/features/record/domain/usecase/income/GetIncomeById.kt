package com.ljmaq.budgetrule.features.record.domain.usecase.income

import com.ljmaq.budgetrule.features.record.domain.model.Income
import com.ljmaq.budgetrule.features.record.domain.repository.IncomeRepository

class GetIncomeById(
    private val repository: IncomeRepository
) {
    suspend operator fun invoke(id: Long): Income? {
        return repository.getIncomeById(id)
    }
}