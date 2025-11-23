package com.ecoworkmonitor.mobile.core.common

import com.ecoworkmonitor.mobile.domain.model.DomainError

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: DomainError) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
