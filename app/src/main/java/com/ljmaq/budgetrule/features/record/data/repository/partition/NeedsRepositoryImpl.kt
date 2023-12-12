package com.ljmaq.budgetrule.features.record.data.repository.partition

import com.ljmaq.budgetrule.features.record.data.datasource.partition.needs.NeedsDao
import com.ljmaq.budgetrule.features.record.domain.model.partition.Needs
import com.ljmaq.budgetrule.features.record.domain.repository.partition.NeedsRepository
import kotlinx.coroutines.flow.Flow

class NeedsRepositoryImpl(
    private val dao: NeedsDao
) : NeedsRepository {
    override fun getNeeds(): Flow<List<Needs>> {
        return dao.getNeeds()
    }

    override suspend fun getNeedsById(id: Long): Needs? {
        return dao.getNeedsById(id)
    }

    override suspend fun insertNeeds(needs: Needs) {
        dao.insertNeeds(needs)
    }

    override suspend fun deleteNeeds(needs: Needs) {
        dao.deleteNeeds(needs)
    }
}