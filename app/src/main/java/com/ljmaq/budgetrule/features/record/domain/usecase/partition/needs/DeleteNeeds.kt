package com.ljmaq.budgetrule.features.record.domain.usecase.partition.needs

import com.ljmaq.budgetrule.features.record.domain.model.partition.Needs
import com.ljmaq.budgetrule.features.record.domain.repository.partition.NeedsRepository

class DeleteNeeds(
    private val repository: NeedsRepository
) {
    suspend operator fun invoke(needs: Needs) {
        repository.deleteNeeds(needs)
    }
}