package com.ljmaq.budgetrule.features.budget_rule.domain.repository

import com.ljmaq.budgetrule.features.budget_rule.domain.model.Income
import kotlinx.coroutines.flow.Flow

interface IncomeRepository {
    fun getAllIncome(): Flow<List<Income>>

    suspend fun getIncomeById(id: Int): Income?

    suspend fun insertIncome(income: Income)

    suspend fun deleteIncome(income: Income)

    suspend fun updateIncome(income: Income)
}