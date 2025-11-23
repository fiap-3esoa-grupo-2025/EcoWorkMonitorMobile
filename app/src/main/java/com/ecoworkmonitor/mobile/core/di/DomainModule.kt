package com.ecoworkmonitor.mobile.core.di

import com.ecoworkmonitor.mobile.data.repository.UserPreferencesRepositoryImpl
import com.ecoworkmonitor.mobile.domain.repository.IAuthRepository
import com.ecoworkmonitor.mobile.domain.repository.IUserPreferencesRepository
import com.ecoworkmonitor.mobile.domain.usecase.LoginUseCase
import com.ecoworkmonitor.mobile.domain.usecase.LogoutUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    @ViewModelScoped
    fun provideLoginUseCase(
        authRepository: IAuthRepository,
        userPreferencesRepository : IUserPreferencesRepository
    ): LoginUseCase {
        return LoginUseCase(authRepository, userPreferencesRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideLogoutUseCase(
        authRepository: IAuthRepository
    ): LogoutUseCase {
        return LogoutUseCase(authRepository)
    }
}
