package com.ljmaq.budgetrule.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveBalanceState(balance: Double)
    fun readBalanceState(): Flow<Double>
}