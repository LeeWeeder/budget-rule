package com.ljmaq.budgetrule.features.budget_rule.domain.usecase.expense

import com.ljmaq.budgetrule.features.budget_rule.domain.model.Expense
import com.ljmaq.budgetrule.features.budget_rule.domain.repository.ExpenseRepository

class DeleteExpense(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(expense: Expense) {
        repository.deleteExpense(expense)
    }
}