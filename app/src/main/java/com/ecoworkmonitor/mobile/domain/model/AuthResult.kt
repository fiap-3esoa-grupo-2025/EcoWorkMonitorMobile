package com.ecoworkmonitor.mobile.domain.model

data class AuthResult(
    val user: User,
    val token: AuthToken
)
