package com.ecoworkmonitor.mobile.core.di

import android.content.Context
import androidx.room.Room
import com.ecoworkmonitor.mobile.data.api.ApiService
import com.ecoworkmonitor.mobile.data.api.FakeApiService
import com.ecoworkmonitor.mobile.data.local.AppDatabase
import com.ecoworkmonitor.mobile.data.local.dao.AlertDao
import com.ecoworkmonitor.mobile.data.repository.AlertRepositoryImpl
import com.ecoworkmonitor.mobile.data.repository.AuthRepositoryImpl
import com.ecoworkmonitor.mobile.data.repository.SensorRepositoryImpl
import com.ecoworkmonitor.mobile.data.repository.UserPreferencesRepositoryImpl
import com.ecoworkmonitor.mobile.domain.repository.IAlertRepository
import com.ecoworkmonitor.mobile.domain.repository.IAuthRepository
import com.ecoworkmonitor.mobile.domain.repository.ISensorRepository
import com.ecoworkmonitor.mobile.domain.repository.IUserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindSensorRepository(
        sensorRepositoryImpl: SensorRepositoryImpl
    ): ISensorRepository

    @Binds
    @Singleton
    abstract fun bindApiService(
        fakeApiService: FakeApiService
    ): ApiService

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): IAuthRepository

    @Binds
    @Singleton
    abstract fun bindAlertRepository(
        alertRepositoryImpl: AlertRepositoryImpl
    ): IAlertRepository

    @Binds
    @Singleton
    abstract fun bindUserPreferencesRepository(
        userPreferencesRepositoryImpl: UserPreferencesRepositoryImpl
    ): IUserPreferencesRepository

    companion object {
        @Provides
        @Singleton
        fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "ecowork_db"
            ).build()
        }

        @Provides
        fun provideAlertDao(database: AppDatabase): AlertDao {
            return database.alertDao()
        }
    }
}