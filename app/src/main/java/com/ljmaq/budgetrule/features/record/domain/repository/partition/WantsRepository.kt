package com.ljmaq.budgetrule.features.record.domain.repository.partition

import com.ljmaq.budgetrule.features.record.domain.model.partition.Wants
import kotlinx.coroutines.flow.Flow

interface WantsRepository {
    fun getWants(): Flow<List<Wants>>

    suspend fun getWantsById(id: Long): Wants?

    suspend fun insertWants(wants: Wants)

    suspend fun deleteWants(wants: Wants)
}