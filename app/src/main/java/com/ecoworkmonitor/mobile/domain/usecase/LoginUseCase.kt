package com.ecoworkmonitor.mobile.domain.usecase

import com.ecoworkmonitor.mobile.core.common.Result
import com.ecoworkmonitor.mobile.domain.model.AuthRequest
import com.ecoworkmonitor.mobile.domain.model.AuthResponse
import com.ecoworkmonitor.mobile.domain.model.DomainError
import com.ecoworkmonitor.mobile.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val authRepository: IAuthRepository,
    private val userPreferencesRepository: com.ecoworkmonitor.mobile.domain.repository.IUserPreferencesRepository
) {
    operator fun invoke(request: AuthRequest): Flow<Result<AuthResponse>> = flow {
        emit(Result.Loading)

        val validationError = request.validate()
        if (validationError != null) {
            emit(Result.Error(DomainError.ValidationError(validationError)))
            return@flow
        }

        when (request) {
            is AuthRequest.Email -> {
                authRepository.login(request.email, request.password).collect { result ->
                    when (result) {
                        is Result.Success -> {
                            // Save tokens to persistent storage
                            userPreferencesRepository.saveTokens(
                                accessToken = result.data.token.accessToken,
                                refreshToken = result.data.token.refreshToken
                            )
                            emit(Result.Success(AuthResponse.Login(result.data)))
                        }
                        is Result.Error -> emit(Result.Error(result.error))
                        is Result.Loading -> emit(Result.Loading)
                    }
                }
            }
            is AuthRequest.Provider -> {
                authRepository.loginWithProvider(request.provider, request.token).collect { result ->
                    when (result) {
                        is Result.Success -> {
                            // Save tokens to persistent storage
                            userPreferencesRepository.saveTokens(
                                accessToken = result.data.token.accessToken,
                                refreshToken = result.data.token.refreshToken
                            )
                            emit(Result.Success(AuthResponse.Login(result.data)))
                        }
                        is Result.Error -> emit(Result.Error(result.error))
                        is Result.Loading -> emit(Result.Loading)
                    }
                }
            }
            is AuthRequest.RefreshToken -> {
                authRepository.refreshToken(request.token).collect { result ->
                    when (result) {
                        is Result.Success -> emit(Result.Success(AuthResponse.TokenRefresh(result.data)))
                        is Result.Error -> emit(Result.Error(result.error))
                        is Result.Loading -> emit(Result.Loading)
                    }
                }
            }
        }
    }
}
