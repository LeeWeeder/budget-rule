package com.ljmaq.budgetrule.features.record.data.repository

import com.ljmaq.budgetrule.features.record.data.datasource.income.IncomeDao
import com.ljmaq.budgetrule.features.record.domain.model.Income
import com.ljmaq.budgetrule.features.record.domain.repository.IncomeRepository
import kotlinx.coroutines.flow.Flow

class IncomeRepositoryImpl(
    private val dao: IncomeDao
) : IncomeRepository {
    override fun getIncomes(): Flow<List<Income>> {
        return dao.getIncomes()
    }

    override suspend fun getIncomeById(id: Long): Income? {
        return dao.getIncomeById(id)
    }

    override suspend fun insertIncome(income: Income) {
        dao.insertIncome(income)
    }

    override suspend fun deleteIncome(income: Income) {
        dao.deleteIncome(income)
    }
}