package com.ecoworkmonitor.mobile.data.repository

import com.ecoworkmonitor.mobile.core.common.Result
import com.ecoworkmonitor.mobile.data.api.FakeAuthApi
import com.ecoworkmonitor.mobile.data.model.LoginRequestDto
import com.ecoworkmonitor.mobile.data.model.ProviderLoginRequestDto
import com.ecoworkmonitor.mobile.domain.model.AuthProvider
import com.ecoworkmonitor.mobile.domain.model.AuthResult
import com.ecoworkmonitor.mobile.domain.model.AuthToken
import com.ecoworkmonitor.mobile.domain.model.User
import com.ecoworkmonitor.mobile.domain.repository.IAuthRepository
import com.ecoworkmonitor.mobile.core.common.safeCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val fakeAuthApi: FakeAuthApi,
    private val userPreferencesRepository: com.ecoworkmonitor.mobile.domain.repository.IUserPreferencesRepository
) : IAuthRepository {
    
    private var currentAccessToken: String? = null
    
    override fun login(email: String, password: String): Flow<Result<AuthResult>> = safeCall {
        val request = LoginRequestDto(email, password)
        val response = fakeAuthApi.login(request)
        
        // Store token for logout
        currentAccessToken = response.token.accessToken
        
        // Map DTO to domain
        AuthResult(
            user = User(
                id = response.user.id,
                email = response.user.email,
                name = response.user.name,
                provider = AuthProvider.valueOf(response.user.provider),
                profilePictureUrl = response.user.profilePictureUrl
            ),
            token = AuthToken(
                accessToken = response.token.accessToken,
                refreshToken = response.token.refreshToken,
                expiresIn = response.token.expiresIn
            )
        )
    }
    
    override fun loginWithProvider(provider: AuthProvider, token: String): Flow<Result<AuthResult>> = safeCall {
        val request = ProviderLoginRequestDto(
            provider = provider.name,
            token = token
        )
        val response = fakeAuthApi.loginWithProvider(request)
        
        // Store token for logout
        currentAccessToken = response.token.accessToken
        
        // Map DTO to domain
        AuthResult(
            user = User(
                id = response.user.id,
                email = response.user.email,
                name = response.user.name,
                provider = AuthProvider.valueOf(response.user.provider),
                profilePictureUrl = response.user.profilePictureUrl
            ),
            token = AuthToken(
                accessToken = response.token.accessToken,
                refreshToken = response.token.refreshToken,
                expiresIn = response.token.expiresIn
            )
        )
    }
    
    override fun refreshToken(refreshToken: String): Flow<Result<AuthToken>> = safeCall {
        val tokenDto = fakeAuthApi.refreshToken(refreshToken)
        
        // Update current token
        currentAccessToken = tokenDto.accessToken
        
        AuthToken(
            accessToken = tokenDto.accessToken,
            refreshToken = tokenDto.refreshToken,
            expiresIn = tokenDto.expiresIn
        )
    }
    
    override fun logout(): Flow<Result<Unit>> = safeCall {
        currentAccessToken?.let { token ->
            fakeAuthApi.logout(token)
        }
        currentAccessToken = null
        userPreferencesRepository.clearTokens()
    }
}
