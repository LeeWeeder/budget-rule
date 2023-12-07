package com.ljmaq.budgetrule.features.record.domain.usecase.partition.wants

import com.ljmaq.budgetrule.features.record.domain.usecase.partition.savings.DeleteSavings
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.savings.GetSavingsById
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.savings.InsertSavings

data class WantsUseCases(
    val getWants: GetWants,
    val deleteWants: DeleteWants,
    val insertWants: InsertWants,
    val getWantsById: GetWantsById
)