package com.ljmaq.budgetrule.features.record.domain.usecase.partition.wants

import com.ljmaq.budgetrule.features.record.domain.model.partition.Wants
import com.ljmaq.budgetrule.features.record.domain.repository.partition.WantsRepository

class InsertWants(
    private val repository: WantsRepository
) {
    suspend operator fun invoke(wants: Wants) {
        repository.insertWants(wants)
    }
}
