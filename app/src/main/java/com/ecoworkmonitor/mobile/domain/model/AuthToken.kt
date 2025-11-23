package com.ecoworkmonitor.mobile.domain.model

data class AuthToken(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long // seconds until expiration
) {
    init {
        require(accessToken.isNotBlank()) { "Access token cannot be blank" }
        require(refreshToken.isNotBlank()) { "Refresh token cannot be blank" }
        require(expiresIn > 0) { "Expiration must be positive: $expiresIn" }
    }
}
