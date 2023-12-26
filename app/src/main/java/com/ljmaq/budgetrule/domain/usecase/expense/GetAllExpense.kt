package com.ljmaq.budgetrule.domain.usecase.expense

import com.ljmaq.budgetrule.domain.model.Expense
import com.ljmaq.budgetrule.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class GetAllExpense(
    private val repository: ExpenseRepository
) {
    operator fun invoke(): Flow<List<Expense>> {
        return repository.getAllExpense()
    }
}