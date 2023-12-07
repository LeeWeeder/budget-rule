package com.ljmaq.budgetrule.features.record.data.repository.partition

import com.ljmaq.budgetrule.features.record.data.datasource.partition.investments.InvestmentsDao
import com.ljmaq.budgetrule.features.record.data.datasource.partition.needs.NeedsDao
import com.ljmaq.budgetrule.features.record.domain.model.partition.Investments
import com.ljmaq.budgetrule.features.record.domain.model.partition.Needs
import com.ljmaq.budgetrule.features.record.domain.repository.partition.InvestmentsRepository
import com.ljmaq.budgetrule.features.record.domain.repository.partition.NeedsRepository
import kotlinx.coroutines.flow.Flow

class InvestmentsRepositoryImpl(
    private val dao: InvestmentsDao
) : InvestmentsRepository {
    override fun getInvestments(): Flow<List<Investments>> {
        return dao.getInvestments()
    }

    override suspend fun getInvestmentsById(id: Int): Investments? {
        return dao.getInvestmentsById(id)
    }

    override suspend fun insertInvestments(investments: Investments) {
        dao.insertInvestments(investments)
    }

    override suspend fun deleteInvestments(investments: Investments) {
        dao.deleteInvestments(investments)
    }
}