package com.ljmaq.budgetrule.features.budget_rule.domain.usecase

import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.expense.DeleteExpense
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.expense.GetAllExpense
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.expense.GetExpenseById
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.expense.InsertExpense
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.expense.UpdateExpense

data class ExpenseUseCases(
    val insertExpense: InsertExpense,
    val updateExpense: UpdateExpense,
    val deleteExpense: DeleteExpense,
    val getAllExpense: GetAllExpense,
    val getExpenseById: GetExpenseById
)