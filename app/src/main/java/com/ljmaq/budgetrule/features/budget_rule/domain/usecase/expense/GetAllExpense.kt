package com.ljmaq.budgetrule.features.budget_rule.domain.usecase.expense

import com.ljmaq.budgetrule.features.budget_rule.domain.model.Expense
import com.ljmaq.budgetrule.features.budget_rule.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class GetAllExpense(
    private val repository: ExpenseRepository
) {
    operator fun invoke(): Flow<List<Expense>> {
        return repository.getAllExpense()
    }
}