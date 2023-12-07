package com.ljmaq.budgetrule.features.record.domain.usecase.partition.investments

import com.ljmaq.budgetrule.features.record.domain.model.partition.Investments
import com.ljmaq.budgetrule.features.record.domain.repository.partition.InvestmentsRepository
import kotlinx.coroutines.flow.Flow

class GetInvestments(
    private val repository: InvestmentsRepository
) {
    operator fun invoke(): Flow<List<Investments>> {
        return repository.getInvestments()
    }
}