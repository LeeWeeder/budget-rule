package com.ljmaq.budgetrule.di

import android.app.Application
import androidx.room.Room
import com.ljmaq.budgetrule.core.data.datasource.BudgetRuleDatabase
import com.ljmaq.budgetrule.data.repository.ExpenseRepositoryImpl
import com.ljmaq.budgetrule.data.repository.IncomeRepositoryImpl
import com.ljmaq.budgetrule.data.repository.PartitionRepositoryImpl
import com.ljmaq.budgetrule.domain.repository.ExpenseRepository
import com.ljmaq.budgetrule.domain.repository.IncomeRepository
import com.ljmaq.budgetrule.domain.repository.PartitionRepository
import com.ljmaq.budgetrule.domain.usecase.ExpenseUseCases
import com.ljmaq.budgetrule.domain.usecase.IncomeUseCases
import com.ljmaq.budgetrule.domain.usecase.PartitionUseCases
import com.ljmaq.budgetrule.domain.usecase.expense.DeleteExpense
import com.ljmaq.budgetrule.domain.usecase.expense.GetAllExpense
import com.ljmaq.budgetrule.domain.usecase.expense.GetExpenseById
import com.ljmaq.budgetrule.domain.usecase.expense.InsertExpense
import com.ljmaq.budgetrule.domain.usecase.expense.UpdateExpense
import com.ljmaq.budgetrule.domain.usecase.income.DeleteIncome
import com.ljmaq.budgetrule.domain.usecase.income.GetAllIncomeDescending
import com.ljmaq.budgetrule.domain.usecase.income.GetIncomeById
import com.ljmaq.budgetrule.domain.usecase.income.InsertIncome
import com.ljmaq.budgetrule.domain.usecase.income.UpdateIncome
import com.ljmaq.budgetrule.domain.usecase.partition.DeletePartition
import com.ljmaq.budgetrule.domain.usecase.partition.GetAllPartition
import com.ljmaq.budgetrule.domain.usecase.partition.GetPartitionById
import com.ljmaq.budgetrule.domain.usecase.partition.InsertPartition
import com.ljmaq.budgetrule.domain.usecase.partition.UpdatePartition
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesBudgetRuleDatabase(app: Application): BudgetRuleDatabase {
        return Room.databaseBuilder(
            app,
            BudgetRuleDatabase::class.java,
            BudgetRuleDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideIncomeRepository(db: BudgetRuleDatabase): IncomeRepository {
        return IncomeRepositoryImpl(db.incomeDao)
    }

    @Provides
    @Singleton
    fun providePartitionRepository(db: BudgetRuleDatabase): PartitionRepository {
        return PartitionRepositoryImpl(db.partitionDao)
    }

    @Provides
    @Singleton
    fun provideExpenseRepository(db: BudgetRuleDatabase): ExpenseRepository {
        return ExpenseRepositoryImpl(db.expenseDao)
    }

    @Provides
    @Singleton
    fun provideIncomeUseCases(repository: IncomeRepository): IncomeUseCases {
        return IncomeUseCases(
            getAllIncomeDescending = GetAllIncomeDescending(repository),
            deleteIncome = DeleteIncome(repository),
            insertIncome = InsertIncome(repository),
            getIncomeById = GetIncomeById(repository),
            updateIncome = UpdateIncome(repository)
        )
    }

    @Provides
    @Singleton
    fun providePartitionUseCases(repository: PartitionRepository): PartitionUseCases {
        return PartitionUseCases(
            getPartitionById = GetPartitionById(repository),
            deletePartition = DeletePartition(repository),
            getAllPartition = GetAllPartition(repository),
            insertPartition = InsertPartition(repository),
            updatePartition = UpdatePartition(repository)
        )
    }

    @Provides
    @Singleton
    fun provideExpenseUseCases(repository: ExpenseRepository): ExpenseUseCases {
        return ExpenseUseCases(
            getExpenseById = GetExpenseById(repository),
            deleteExpense = DeleteExpense(repository),
            getAllExpense = GetAllExpense(repository),
            insertExpense = InsertExpense(repository),
            updateExpense = UpdateExpense(repository)
        )
    }
}