package com.ljmaq.budgetrule.features.record.domain.repository

import com.ljmaq.budgetrule.features.record.domain.model.Income
import kotlinx.coroutines.flow.Flow

interface IncomeRepository {
    fun getIncomes(): Flow<List<Income>>

    suspend fun getIncomeById(id: Long): Income?

    suspend fun insertIncome(income: Income)

    suspend fun deleteIncome(income: Income)
}