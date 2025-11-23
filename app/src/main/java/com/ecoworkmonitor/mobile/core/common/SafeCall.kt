package com.ecoworkmonitor.mobile.core.common

import android.net.http.HttpException
import com.ecoworkmonitor.mobile.domain.model.DomainError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import java.io.IOException

inline fun <T> safeCall(crossinline apiCall: suspend () -> T): Flow<Result<T>> = flow {
    try {
        emit(Result.Success(apiCall()))
    } catch (e: Exception) {
        val domainError = when (e) {
            is IOException -> DomainError.NetworkError(e)
            else -> DomainError.UnknownError(e)
        }
        emit(Result.Error(domainError))
    }
}.onStart { emit(Result.Loading) }
