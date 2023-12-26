package com.ljmaq.budgetrule.data.repository

import com.ljmaq.budgetrule.data.datasource.IncomeDao
import com.ljmaq.budgetrule.domain.model.Income
import com.ljmaq.budgetrule.domain.repository.IncomeRepository
import kotlinx.coroutines.flow.Flow

class IncomeRepositoryImpl(
    private val dao: IncomeDao
) : IncomeRepository {
    override fun getAllIncome(): Flow<List<Income>> {
        return dao.getAllIncome()
    }

    override suspend fun getIncomeById(id: Int): Income? {
        return dao.getIncomeById(id)
    }

    override suspend fun insertIncome(income: Income) {
        dao.insertIncome(income)
    }

    override suspend fun deleteIncome(income: Income) {
        dao.deleteIncome(income)
    }

    override suspend fun updateIncome(income: Income) {
        dao.updateIncome(income)
    }
}