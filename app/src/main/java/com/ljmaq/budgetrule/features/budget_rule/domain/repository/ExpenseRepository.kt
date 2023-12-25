package com.ljmaq.budgetrule.features.budget_rule.domain.repository

import com.ljmaq.budgetrule.features.budget_rule.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getAllExpense(): Flow<List<Expense>>

    suspend fun getExpenseById(id: Int): Expense?

    suspend fun insertExpense(expense: Expense)

    suspend fun deleteExpense(expense: Expense)

    suspend fun updateExpense(expense: Expense)
}