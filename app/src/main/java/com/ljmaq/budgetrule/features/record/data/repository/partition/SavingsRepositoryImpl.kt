package com.ljmaq.budgetrule.features.record.data.repository.partition

import com.ljmaq.budgetrule.features.record.data.datasource.partition.savings.SavingsDao
import com.ljmaq.budgetrule.features.record.domain.model.partition.Savings
import com.ljmaq.budgetrule.features.record.domain.repository.partition.SavingsRepository
import kotlinx.coroutines.flow.Flow

class SavingsRepositoryImpl(
    private val dao: SavingsDao
) : SavingsRepository {
    override fun getSavings(): Flow<List<Savings>> {
        return dao.getSavings()
    }

    override suspend fun getSavingsById(id: Long): Savings? {
        return dao.getSavingsById(id)
    }

    override suspend fun insertSavings(savings: Savings) {
        dao.insertSavings(savings)
    }

    override suspend fun deleteSavings(savings: Savings) {
        dao.deleteSavings(savings)
    }
}