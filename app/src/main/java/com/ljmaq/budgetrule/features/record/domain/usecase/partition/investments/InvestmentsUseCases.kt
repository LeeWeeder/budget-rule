package com.ljmaq.budgetrule.features.record.domain.usecase.partition.investments

data class InvestmentsUseCases(
    val getInvestments: GetInvestments,
    val deleteInvestments: DeleteInvestments,
    val insertInvestments: InsertInvestments,
    val getInvestmentsById: GetInvestmentsById,
    val getInvestmentsDescending: GetInvestmentsDescending
)