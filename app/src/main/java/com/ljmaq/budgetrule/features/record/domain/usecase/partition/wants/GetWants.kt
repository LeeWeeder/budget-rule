package com.ljmaq.budgetrule.features.record.domain.usecase.partition.wants

import com.ljmaq.budgetrule.features.record.domain.model.partition.Wants
import com.ljmaq.budgetrule.features.record.domain.repository.partition.WantsRepository
import kotlinx.coroutines.flow.Flow

class GetWants(
    private val repository: WantsRepository
) {
    operator fun invoke(): Flow<List<Wants>> {
        return repository.getWants()
    }
}