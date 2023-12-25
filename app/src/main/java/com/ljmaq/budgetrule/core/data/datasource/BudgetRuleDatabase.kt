package com.ljmaq.budgetrule.core.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ljmaq.budgetrule.features.budget_rule.data.datasource.ExpenseDao
import com.ljmaq.budgetrule.features.budget_rule.data.datasource.IncomeDao
import com.ljmaq.budgetrule.features.budget_rule.data.datasource.PartitionDao
import com.ljmaq.budgetrule.features.budget_rule.domain.model.Expense
import com.ljmaq.budgetrule.features.budget_rule.domain.model.Income
import com.ljmaq.budgetrule.features.budget_rule.domain.model.Partition

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