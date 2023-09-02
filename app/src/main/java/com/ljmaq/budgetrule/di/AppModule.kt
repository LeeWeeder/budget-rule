package com.ljmaq.budgetrule.di

import android.app.Application
import androidx.room.Room
import com.ljmaq.budgetrule.features.record.data.datasource.RecordDatabase
import com.ljmaq.budgetrule.features.record.data.repository.RecordRepositoryImpl
import com.ljmaq.budgetrule.features.record.domain.repository.RecordRepository
import com.ljmaq.budgetrule.features.record.domain.usecase.AddRecord
import com.ljmaq.budgetrule.features.record.domain.usecase.DeleteRecord
import com.ljmaq.budgetrule.features.record.domain.usecase.GetRecord
import com.ljmaq.budgetrule.features.record.domain.usecase.GetRecords
import com.ljmaq.budgetrule.features.record.domain.usecase.RecordsUseCases
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
    fun providesRecordDatabase(app: Application): RecordDatabase {
        return Room.databaseBuilder(
            app,
            RecordDatabase::class.java,
            RecordDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRecordRepository(db: RecordDatabase): RecordRepository {
        return RecordRepositoryImpl(db.recordDao)
    }

    @Provides
    @Singleton
    fun provideRecordUseCases(repository: RecordRepository): RecordsUseCases {
        return RecordsUseCases(
            getRecords = GetRecords(repository),
            deleteRecord = DeleteRecord(repository),
            addRecord = AddRecord(repository),
            getRecord = GetRecord(repository)
        )
    }
}