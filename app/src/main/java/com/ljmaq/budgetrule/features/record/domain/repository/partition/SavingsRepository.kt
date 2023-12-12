package com.ljmaq.budgetrule.features.record.domain.repository.partition

import com.ljmaq.budgetrule.features.record.domain.model.partition.Savings
import kotlinx.coroutines.flow.Flow

interface SavingsRepository {
    fun getSavings(): Flow<List<Savings>>

    suspend fun getSavingsById(id: Long): Savings?

    suspend fun insertSavings(savings: Savings)

    suspend fun deleteSavings(savings: Savings)
}