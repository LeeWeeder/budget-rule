package com.ljmaq.budgetrule.features.budget_rule.data.repository

import com.ljmaq.budgetrule.features.budget_rule.data.datasource.ExpenseDao
import com.ljmaq.budgetrule.features.budget_rule.domain.model.Expense
import com.ljmaq.budgetrule.features.budget_rule.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class ExpenseRepositoryImpl(
    private val dao: ExpenseDao
): ExpenseRepository {
    override fun getAllExpense(): Flow<List<Expense>> {
        return dao.getAllExpense()
    }

    override suspend fun getExpenseById(id: Int): Expense? {
        return dao.getExpenseById(id)
    }

    override suspend fun insertExpense(expense: Expense) {
        dao.insertExpense(expense)
    }

    override suspend fun deleteExpense(expense: Expense) {
        dao.deleteExpense(expense)
    }

    override suspend fun updateExpense(expense: Expense) {
        dao.updateExpense(expense)
    }
}