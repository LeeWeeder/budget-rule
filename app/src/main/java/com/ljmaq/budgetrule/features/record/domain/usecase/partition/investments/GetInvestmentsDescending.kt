package com.ljmaq.budgetrule.features.record.domain.usecase.partition.investments

import com.ljmaq.budgetrule.features.record.domain.model.partition.Investments
import com.ljmaq.budgetrule.features.record.domain.repository.partition.InvestmentsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetInvestmentsDescending(
    private val repository: InvestmentsRepository
) {
    operator fun invoke(): Flow<List<Investments>> {
        return repository.getInvestments().map { investments ->
            investments.sortedByDescending {
                it.timestamp
            }
        }
    }
}