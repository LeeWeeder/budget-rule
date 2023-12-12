package com.ljmaq.budgetrule.features.record.data.datasource.income

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ljmaq.budgetrule.features.record.domain.model.Income
import kotlinx.coroutines.flow.Flow

@Dao
interface IncomeDao {
    @Query("SELECT * FROM income")
    fun getIncomes(): Flow<List<Income>>

    @Query("SELECT * FROM income WHERE timestamp = :id")
    suspend fun getIncomeById(id: Long): Income?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncome(income: Income)

    @Delete
    suspend fun deleteIncome(income: Income)
}