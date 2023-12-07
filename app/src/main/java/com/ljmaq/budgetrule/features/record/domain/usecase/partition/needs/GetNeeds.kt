package com.ljmaq.budgetrule.features.record.domain.usecase.partition.needs

import com.ljmaq.budgetrule.features.record.domain.model.partition.Needs
import com.ljmaq.budgetrule.features.record.domain.repository.partition.NeedsRepository
import kotlinx.coroutines.flow.Flow

class GetNeeds(
    private val repository: NeedsRepository
) {
    operator fun invoke(): Flow<List<Needs>> {
        return repository.getNeeds()
    }
}