package com.ljmaq.budgetrule.domain.repository

import com.ljmaq.budgetrule.domain.model.Income
import kotlinx.coroutines.flow.Flow

interface IncomeRepository {
    fun getAllIncome(): Flow<List<Income>>

    suspend fun getIncomeById(id: Int): Income?

    suspend fun insertIncome(income: Income)

    suspend fun deleteIncome(income: Income)

    suspend fun updateIncome(income: Income)
}