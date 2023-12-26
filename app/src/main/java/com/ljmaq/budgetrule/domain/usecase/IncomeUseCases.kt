package com.ljmaq.budgetrule.domain.usecase

import com.ljmaq.budgetrule.domain.usecase.income.DeleteIncome
import com.ljmaq.budgetrule.domain.usecase.income.GetAllIncomeDescending
import com.ljmaq.budgetrule.domain.usecase.income.GetIncomeById
import com.ljmaq.budgetrule.domain.usecase.income.InsertIncome
import com.ljmaq.budgetrule.domain.usecase.income.UpdateIncome

data class IncomeUseCases(
    val getAllIncomeDescending: GetAllIncomeDescending,
    val deleteIncome: DeleteIncome,
    val insertIncome: InsertIncome,
    val getIncomeById: GetIncomeById,
    val updateIncome: UpdateIncome
)