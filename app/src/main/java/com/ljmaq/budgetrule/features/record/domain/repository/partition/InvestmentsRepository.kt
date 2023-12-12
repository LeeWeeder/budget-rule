package com.ljmaq.budgetrule.features.record.domain.repository.partition

import com.ljmaq.budgetrule.features.record.domain.model.partition.Investments
import kotlinx.coroutines.flow.Flow

interface InvestmentsRepository {
    fun getInvestments(): Flow<List<Investments>>

    suspend fun getInvestmentsById(id: Long): Investments?

    suspend fun insertInvestments(investments: Investments)

    suspend fun deleteInvestments(investments: Investments)
}