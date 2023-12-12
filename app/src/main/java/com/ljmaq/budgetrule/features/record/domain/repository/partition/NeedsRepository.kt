package com.ljmaq.budgetrule.features.record.domain.repository.partition

import com.ljmaq.budgetrule.features.record.domain.model.partition.Needs
import kotlinx.coroutines.flow.Flow

interface NeedsRepository {
    fun getNeeds(): Flow<List<Needs>>

    suspend fun getNeedsById(id: Long): Needs?

    suspend fun insertNeeds(needs: Needs)

    suspend fun deleteNeeds(needs: Needs)
}