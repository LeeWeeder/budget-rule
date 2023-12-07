package com.ljmaq.budgetrule.features.record.domain.usecase.partition.investments

import com.ljmaq.budgetrule.features.record.domain.model.partition.Investments
import com.ljmaq.budgetrule.features.record.domain.repository.partition.InvestmentsRepository

class DeleteInvestments(
    private val repository: InvestmentsRepository
) {
    suspend operator fun invoke(investments: Investments) {
        repository.deleteInvestments(investments)
    }
}