package com.ljmaq.budgetrule.domain.repository

import androidx.annotation.FloatRange
import com.ljmaq.budgetrule.domain.model.Partition
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveBalanceState(balance: Double)
    suspend fun saveExcessPartitionState(amount: Double, @FloatRange(from = 0.0, to = 1.0) sharePercent: Float)
    fun readBalanceState(): Flow<Double>
    fun readExcessPartitionState(): Flow<Partition>

    suspend fun saveOnBoardingState(showOnBoarding: Boolean)
    fun readOnBoardingState(): Flow<Boolean>

    suspend fun saveCurrencyState(currencyCode: String)
    fun readCurrencyState(): Flow<String>
}