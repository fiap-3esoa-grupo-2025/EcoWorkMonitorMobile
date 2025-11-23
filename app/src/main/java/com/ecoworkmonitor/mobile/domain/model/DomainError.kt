package com.ecoworkmonitor.mobile.domain.model

sealed interface DomainError {
    data class NetworkError(val throwable: Throwable? = null) : DomainError
    data class ServerError(val code: Int, val message: String?) : DomainError
    data class AuthError(val message: String?) : DomainError
    data class ValidationError(val message: String) : DomainError
    data class UnknownError(val throwable: Throwable? = null) : DomainError
}
