package com.ljmaq.budgetrule.features.record.data.datasource.partition.savings

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.ljmaq.budgetrule.features.record.domain.model.partition.Needs
import com.ljmaq.budgetrule.features.record.domain.model.partition.Savings

@Dao
interface SavingsDao {
    @Query("SELECT * FROM savings")
    fun getSavings(): Flow<List<Savings>>

    @Query("SELECT * FROM savings WHERE id = :id")
    suspend fun getSavingsById(id: Int): Savings?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavings(savings: Savings)

    @Delete
    suspend fun deleteSavings(savings: Savings)
}