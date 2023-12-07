package com.ljmaq.budgetrule.features.record.domain.usecase.income

data class IncomesUseCases(
    val getIncomesDescending: GetIncomesDescending,
    val deleteIncome: DeleteIncome,
    val insertIncome: InsertIncome,
    val getIncomeById: GetIncomeById
)