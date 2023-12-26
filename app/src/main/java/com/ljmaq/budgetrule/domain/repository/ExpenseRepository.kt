package com.ljmaq.budgetrule.domain.repository

import com.ljmaq.budgetrule.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getAllExpense(): Flow<List<Expense>>

    suspend fun getExpenseById(id: Int): Expense?

    suspend fun insertExpense(expense: Expense)

    suspend fun deleteExpense(expense: Expense)

    suspend fun updateExpense(expense: Expense)
}