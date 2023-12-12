package com.ljmaq.budgetrule.features.record.domain.usecase.partition.needs

data class NeedsUseCases(
    val getNeeds: GetNeeds,
    val deleteNeeds: DeleteNeeds,
    val insertNeeds: InsertNeeds,
    val getNeedsById: GetNeedsById,
    val getNeedsDescending: GetNeedsDescending
)