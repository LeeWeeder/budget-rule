package com.ljmaq.budgetrule.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ljmaq.budgetrule.data.datasource.BudgetRuleDatabase
import com.ljmaq.budgetrule.data.repository.DataStoreRepositoryImpl
import com.ljmaq.budgetrule.data.repository.ExpenseRepositoryImpl
import com.ljmaq.budgetrule.data.repository.IncomeRepositoryImpl
import com.ljmaq.budgetrule.data.repository.PartitionRepositoryImpl
import com.ljmaq.budgetrule.domain.repository.DataStoreRepository
import com.ljmaq.budgetrule.domain.repository.ExpenseRepository
import com.ljmaq.budgetrule.domain.repository.IncomeRepository
import com.ljmaq.budgetrule.domain.repository.PartitionRepository
import com.ljmaq.budgetrule.domain.usecase.DataStoreUseCases
import com.ljmaq.budgetrule.domain.usecase.ExpenseUseCases
import com.ljmaq.budgetrule.domain.usecase.IncomeUseCases
import com.ljmaq.budgetrule.domain.usecase.PartitionUseCases
import com.ljmaq.budgetrule.domain.usecase.dataStore.ReadBalanceState
import com.ljmaq.budgetrule.domain.usecase.dataStore.ReadCurrencyState
import com.ljmaq.budgetrule.domain.usecase.dataStore.ReadExcessPartitionState
import com.ljmaq.budgetrule.domain.usecase.dataStore.ReadOnBoardingState
import com.ljmaq.budgetrule.domain.usecase.dataStore.SaveBalanceState
import com.ljmaq.budgetrule.domain.usecase.dataStore.SaveCurrencyState
import com.ljmaq.budgetrule.domain.usecase.dataStore.SaveExcessPartitionState
import com.ljmaq.budgetrule.domain.usecase.dataStore.SaveOnBoardingState
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
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ): DataStoreRepository = DataStoreRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideDataStoreUseCases(repository: DataStoreRepository): DataStoreUseCases {
        return DataStoreUseCases(
            saveBalanceState = SaveBalanceState(repository),
            readBalanceState = ReadBalanceState(repository),
            saveExcessPartitionState = SaveExcessPartitionState(repository),
            readExcessPartitionState = ReadExcessPartitionState(repository),
            saveOnBoardingState = SaveOnBoardingState(repository),
            readOnBoardingState = ReadOnBoardingState(repository),
            saveCurrencyState = SaveCurrencyState(repository),
            readCurrencyState = ReadCurrencyState(repository)
        )
    }
}