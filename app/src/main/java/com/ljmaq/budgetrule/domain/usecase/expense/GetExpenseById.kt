package com.ljmaq.budgetrule.domain.usecase.expense

import com.ljmaq.budgetrule.domain.model.Expense
import com.ljmaq.budgetrule.domain.repository.ExpenseRepository

class GetExpenseById(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(id: Int): Expense? {
        return repository.getExpenseById(id)
    }
}