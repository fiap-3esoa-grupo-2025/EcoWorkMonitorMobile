package com.ecoworkmonitor.mobile.domain.repository

import com.ecoworkmonitor.mobile.core.common.Result
import com.ecoworkmonitor.mobile.domain.model.AuthProvider
import com.ecoworkmonitor.mobile.domain.model.AuthResult
import com.ecoworkmonitor.mobile.domain.model.AuthToken
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    /**
     * Login with email and password
     */
    fun login(email: String, password: String): Flow<Result<AuthResult>>
    
    /**
     * Login with third-party provider (Google, GitHub, Microsoft)
     */
    fun loginWithProvider(provider: AuthProvider, token: String): Flow<Result<AuthResult>>
    
    /**
     * Refresh access token using refresh token
     */
    fun refreshToken(refreshToken: String): Flow<Result<AuthToken>>
    
    /**
     * Logout current user
     */
    fun logout(): Flow<Result<Unit>>
}
