package com.ecoworkmonitor.mobile.data.model

data class AuthTokenDto(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long
)
