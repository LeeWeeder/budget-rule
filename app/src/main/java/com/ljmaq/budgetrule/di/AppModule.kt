package com.ljmaq.budgetrule.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ljmaq.budgetrule.core.data.datasource.BudgetRuleDatabase
import com.ljmaq.budgetrule.features.budget_rule.data.repository.ExpenseRepositoryImpl
import com.ljmaq.budgetrule.features.budget_rule.data.repository.IncomeRepositoryImpl
import com.ljmaq.budgetrule.features.budget_rule.data.repository.PartitionRepositoryImpl
import com.ljmaq.budgetrule.features.budget_rule.domain.repository.ExpenseRepository
import com.ljmaq.budgetrule.features.budget_rule.domain.repository.IncomeRepository
import com.ljmaq.budgetrule.features.budget_rule.domain.repository.PartitionRepository
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.ExpenseUseCases
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.IncomeUseCases
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.PartitionUseCases
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.expense.DeleteExpense
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.expense.GetAllExpense
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.expense.GetExpenseById
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.expense.InsertExpense
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.expense.UpdateExpense
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.income.DeleteIncome
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.income.GetAllIncomeDescending
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.income.GetIncomeById
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.income.InsertIncome
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.income.UpdateIncome
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.partition.DeletePartition
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.partition.GetAllPartition
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.partition.GetPartitionById
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.partition.InsertPartition
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.partition.UpdatePartition
import com.ljmaq.budgetrule.features.onboarding.data.repository.OnBoardingRepositoryImpl
import com.ljmaq.budgetrule.features.onboarding.domain.repository.OnBoardingRepository
import com.ljmaq.budgetrule.features.onboarding.domain.usecase.OnBoardingUseCases
import com.ljmaq.budgetrule.features.onboarding.domain.usecase.ReadOnBoardingState
import com.ljmaq.budgetrule.features.onboarding.domain.usecase.SaveOnBoardingState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun providesOnBoardingRepository(
        @ApplicationContext context: Context
    ): OnBoardingRepository {
        return OnBoardingRepositoryImpl(context = context)
    }

    @Provides
    @Singleton
    fun providesOnBoardingUseCases(repository: OnBoardingRepository) : OnBoardingUseCases {
        return OnBoardingUseCases(
            saveOnBoardingState = SaveOnBoardingState(repository),
            readOnBoardingState = ReadOnBoardingState(repository)
        )
    }
}