package com.ecoworkmonitor.mobile.domain.usecase

import com.ecoworkmonitor.mobile.core.common.Result
import com.ecoworkmonitor.mobile.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: IAuthRepository
) {
    operator fun invoke(): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        
        authRepository.logout().collect { result ->
            emit(result)
        }
    }
}
