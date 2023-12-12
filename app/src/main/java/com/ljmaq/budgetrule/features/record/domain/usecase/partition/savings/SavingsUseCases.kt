package com.ljmaq.budgetrule.features.record.domain.usecase.partition.savings

data class SavingsUseCases(
    val getSavings: GetSavings,
    val deleteSavings: DeleteSavings,
    val insertSavings: InsertSavings,
    val getSavingsById: GetSavingsById,
    val getSavingsDescending: GetSavingsDescending
)