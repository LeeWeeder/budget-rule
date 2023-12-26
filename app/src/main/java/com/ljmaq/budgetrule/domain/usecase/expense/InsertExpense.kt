package com.ljmaq.budgetrule.domain.usecase.expense

import com.ljmaq.budgetrule.domain.model.Expense
import com.ljmaq.budgetrule.domain.repository.ExpenseRepository

class InsertExpense(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(expense: Expense) {
        repository.insertExpense(expense)
    }
}