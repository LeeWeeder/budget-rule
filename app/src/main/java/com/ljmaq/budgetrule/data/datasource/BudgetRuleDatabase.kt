package com.ljmaq.budgetrule.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ljmaq.budgetrule.domain.model.Expense
import com.ljmaq.budgetrule.domain.model.Income
import com.ljmaq.budgetrule.domain.model.Partition

@Database(
    entities = [Partition::class, Income::class, Expense::class],
    version = 1
)
abstract class BudgetRuleDatabase: RoomDatabase() {
    abstract val partitionDao: PartitionDao
    abstract val incomeDao: IncomeDao
    abstract val expenseDao: ExpenseDao

    companion object {
        const val DATABASE_NAME = "budget_rule_db"
    }
}