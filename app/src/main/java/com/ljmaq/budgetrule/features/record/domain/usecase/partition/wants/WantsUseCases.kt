package com.ljmaq.budgetrule.features.record.domain.usecase.partition.wants

data class WantsUseCases(
    val getWants: GetWants,
    val deleteWants: DeleteWants,
    val insertWants: InsertWants,
    val getWantsById: GetWantsById,
    val getWantsDescending: GetWantsDescending
)