package com.ecoworkmonitor.mobile.domain.model

sealed interface AuthResponse {
    data class Login(val result: AuthResult) : AuthResponse
    data class TokenRefresh(val token: AuthToken) : AuthResponse
}
