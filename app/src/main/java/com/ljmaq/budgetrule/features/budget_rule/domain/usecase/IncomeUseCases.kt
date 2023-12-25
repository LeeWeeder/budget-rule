package com.ljmaq.budgetrule.features.budget_rule.domain.usecase

import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.income.DeleteIncome
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.income.GetAllIncomeDescending
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.income.GetIncomeById
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.income.InsertIncome
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.income.UpdateIncome

data class IncomeUseCases(
    val getAllIncomeDescending: GetAllIncomeDescending,
    val deleteIncome: DeleteIncome,
    val insertIncome: InsertIncome,
    val getIncomeById: GetIncomeById,
    val updateIncome: UpdateIncome
)