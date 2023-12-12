package com.ljmaq.budgetrule.features.record.domain.usecase.partition.wants

import com.ljmaq.budgetrule.features.record.domain.model.partition.Wants
import com.ljmaq.budgetrule.features.record.domain.repository.partition.WantsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetWantsDescending(
    private val repository: WantsRepository
) {
    operator fun invoke(): Flow<List<Wants>> {
        return repository.getWants().map { wants ->
            wants.sortedByDescending {
                it.timestamp
            }
        }
    }
}