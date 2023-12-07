package com.ljmaq.budgetrule.features.record.domain.usecase.partition.needs

import com.ljmaq.budgetrule.features.record.domain.model.partition.Needs
import com.ljmaq.budgetrule.features.record.domain.repository.partition.NeedsRepository

class InsertNeeds(
    private val repository: NeedsRepository
) {
    suspend operator fun invoke(needs: Needs) {
        repository.insertNeeds(needs)
    }
}
