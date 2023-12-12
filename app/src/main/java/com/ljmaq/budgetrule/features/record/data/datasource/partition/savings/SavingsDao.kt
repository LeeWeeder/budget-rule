package com.ljmaq.budgetrule.features.record.data.datasource.partition.savings

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ljmaq.budgetrule.features.record.domain.model.partition.Savings
import kotlinx.coroutines.flow.Flow

@Dao
interface SavingsDao {
    @Query("SELECT * FROM savings")
    fun getSavings(): Flow<List<Savings>>

    @Query("SELECT * FROM savings WHERE timestamp = :id")
    suspend fun getSavingsById(id: Long): Savings?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavings(savings: Savings)

    @Delete
    suspend fun deleteSavings(savings: Savings)
}