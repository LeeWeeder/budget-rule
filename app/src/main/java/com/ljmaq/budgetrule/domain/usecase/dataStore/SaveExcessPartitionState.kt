package com.ljmaq.budgetrule.domain.usecase.dataStore

import androidx.annotation.FloatRange
import com.ljmaq.budgetrule.domain.repository.DataStoreRepository

class SaveExcessPartitionState(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke(amount: Double, @FloatRange(from = 0.0, to = 1.0) sharePercent: Float) {
        repository.saveExcessPartitionState(amount = amount, sharePercent = sharePercent)
    }
}