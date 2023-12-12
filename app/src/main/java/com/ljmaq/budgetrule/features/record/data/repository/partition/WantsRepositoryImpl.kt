package com.ljmaq.budgetrule.features.record.data.repository.partition

import com.ljmaq.budgetrule.features.record.data.datasource.partition.wants.WantsDao
import com.ljmaq.budgetrule.features.record.domain.model.partition.Wants
import com.ljmaq.budgetrule.features.record.domain.repository.partition.WantsRepository
import kotlinx.coroutines.flow.Flow

class WantsRepositoryImpl(
    private val dao: WantsDao
) : WantsRepository {
    override fun getWants(): Flow<List<Wants>> {
        return dao.getWants()
    }

    override suspend fun getWantsById(id: Long): Wants? {
        return dao.getWantsById(id)
    }

    override suspend fun insertWants(wants: Wants) {
        dao.insertWants(wants)
    }

    override suspend fun deleteWants(wants: Wants) {
        dao.deleteWants(wants)
    }
}