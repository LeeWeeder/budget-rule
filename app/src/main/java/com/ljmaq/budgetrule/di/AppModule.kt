package com.ljmaq.budgetrule.di

import android.app.Application
import androidx.room.Room
import com.ljmaq.budgetrule.features.record.data.datasource.income.IncomeDatabase
import com.ljmaq.budgetrule.features.record.data.datasource.partition.investments.InvestmentsDatabase
import com.ljmaq.budgetrule.features.record.data.datasource.partition.needs.NeedsDatabase
import com.ljmaq.budgetrule.features.record.data.datasource.partition.savings.SavingsDatabase
import com.ljmaq.budgetrule.features.record.data.datasource.partition.wants.WantsDatabase
import com.ljmaq.budgetrule.features.record.data.repository.IncomeRepositoryImpl
import com.ljmaq.budgetrule.features.record.data.repository.partition.InvestmentsRepositoryImpl
import com.ljmaq.budgetrule.features.record.data.repository.partition.NeedsRepositoryImpl
import com.ljmaq.budgetrule.features.record.data.repository.partition.SavingsRepositoryImpl
import com.ljmaq.budgetrule.features.record.data.repository.partition.WantsRepositoryImpl
import com.ljmaq.budgetrule.features.record.domain.repository.IncomeRepository
import com.ljmaq.budgetrule.features.record.domain.repository.partition.InvestmentsRepository
import com.ljmaq.budgetrule.features.record.domain.repository.partition.NeedsRepository
import com.ljmaq.budgetrule.features.record.domain.repository.partition.SavingsRepository
import com.ljmaq.budgetrule.features.record.domain.repository.partition.WantsRepository
import com.ljmaq.budgetrule.features.record.domain.usecase.income.InsertIncome
import com.ljmaq.budgetrule.features.record.domain.usecase.income.DeleteIncome
import com.ljmaq.budgetrule.features.record.domain.usecase.income.GetIncomeById
import com.ljmaq.budgetrule.features.record.domain.usecase.income.GetIncomesDescending
import com.ljmaq.budgetrule.features.record.domain.usecase.income.IncomesUseCases
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.investments.DeleteInvestments
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.investments.GetInvestments
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.investments.GetInvestmentsById
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.investments.InsertInvestments
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.investments.InvestmentsUseCases
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.needs.DeleteNeeds
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.needs.GetNeeds
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.needs.GetNeedsById
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.needs.InsertNeeds
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.needs.NeedsUseCases
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.savings.DeleteSavings
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.savings.GetSavings
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.savings.GetSavingsById
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.savings.InsertSavings
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.savings.SavingsUseCases
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.wants.DeleteWants
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.wants.GetWants
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.wants.GetWantsById
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.wants.InsertWants
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.wants.WantsUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Income
    @Provides
    @Singleton
    fun providesIncomeDatabase(app: Application): IncomeDatabase {
        return Room.databaseBuilder(
            app,
            IncomeDatabase::class.java,
            IncomeDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideIncomeRepository(db: IncomeDatabase): IncomeRepository {
        return IncomeRepositoryImpl(db.incomeDao)
    }

    @Provides
    @Singleton
    fun provideIncomeUseCases(repository: IncomeRepository): IncomesUseCases {
        return IncomesUseCases(
            getIncomesDescending = GetIncomesDescending(repository),
            deleteIncome = DeleteIncome(repository),
            insertIncome = InsertIncome(repository),
            getIncomeById = GetIncomeById(repository)
        )
    }

    // Partition
    // Needs
    @Provides
    @Singleton
    fun providesNeedsDatabase(app: Application): NeedsDatabase {
        return Room.databaseBuilder(
            app,
            NeedsDatabase::class.java,
            NeedsDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNeedsRepository(db: NeedsDatabase): NeedsRepository {
        return NeedsRepositoryImpl(db.needsDao)
    }

    @Provides
    @Singleton
    fun provideNeedsUseCases(repository: NeedsRepository): NeedsUseCases {
        return NeedsUseCases(
            getNeeds = GetNeeds(repository),
            deleteNeeds = DeleteNeeds(repository),
            insertNeeds = InsertNeeds(repository),
            getNeedsById = GetNeedsById(repository)
        )
    }

    // Wants
    @Provides
    @Singleton
    fun providesWantsDatabase(app: Application): WantsDatabase {
        return Room.databaseBuilder(
            app,
            WantsDatabase::class.java,
            WantsDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideWantsRepository(db: WantsDatabase): WantsRepository {
        return WantsRepositoryImpl(db.wantsDao)
    }

    @Provides
    @Singleton
    fun provideWantsUseCases(repository: WantsRepository): WantsUseCases {
        return WantsUseCases(
            getWants = GetWants(repository),
            getWantsById = GetWantsById(repository),
            deleteWants = DeleteWants(repository),
            insertWants = InsertWants(repository)
        )
    }

    // Savings
    @Provides
    @Singleton
    fun providesSavingsDatabase(app: Application): SavingsDatabase {
        return Room.databaseBuilder(
            app,
            SavingsDatabase::class.java,
            SavingsDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideSavingsRepository(db: SavingsDatabase): SavingsRepository {
        return SavingsRepositoryImpl(db.savingsDao)
    }

    @Provides
    @Singleton
    fun provideSavingsUseCases(repository: SavingsRepository): SavingsUseCases {
        return SavingsUseCases(
            getSavings = GetSavings(repository),
            getSavingsById = GetSavingsById(repository),
            insertSavings = InsertSavings(repository),
            deleteSavings = DeleteSavings(repository)
        )
    }

    // Investments
    @Provides
    @Singleton
    fun providesInvestmentsDatabase(app: Application): InvestmentsDatabase {
        return Room.databaseBuilder(
            app,
            InvestmentsDatabase::class.java,
            InvestmentsDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideInvestmentsRepository(db: InvestmentsDatabase): InvestmentsRepository {
        return InvestmentsRepositoryImpl(db.investmentsDao)
    }

    @Provides
    @Singleton
    fun provideInvestmentsUseCases(repository: InvestmentsRepository): InvestmentsUseCases {
        return InvestmentsUseCases(
            getInvestments = GetInvestments(repository),
            getInvestmentsById = GetInvestmentsById(repository),
            insertInvestments = InsertInvestments(repository),
            deleteInvestments = DeleteInvestments(repository)
        )
    }
}