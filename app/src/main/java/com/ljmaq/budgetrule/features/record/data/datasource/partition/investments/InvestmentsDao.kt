package com.ljmaq.budgetrule.features.record.data.datasource.partition.investments

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ljmaq.budgetrule.features.record.domain.model.partition.Investments
import kotlinx.coroutines.flow.Flow
import com.ljmaq.budgetrule.features.record.domain.model.partition.Needs

@Dao
interface InvestmentsDao {
    @Query("SELECT * FROM investments")
    fun getInvestments(): Flow<List<Investments>>

    @Query("SELECT * FROM investments WHERE id = :id")
    suspend fun getInvestmentsById(id: Int): Investments?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInvestments(investments: Investments)

    @Delete
    suspend fun deleteInvestments(investments: Investments)
}