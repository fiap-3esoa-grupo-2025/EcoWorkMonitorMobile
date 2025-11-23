package com.ecoworkmonitor.mobile.domain.model

import android.util.Patterns

data class User(
    val id: String,
    val email: String,
    val name: String,
    val provider: AuthProvider,
    val profilePictureUrl: String? = null
) {
    init {
        require(id.isNotBlank()) { "User ID cannot be blank" }
        require(email.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) { "Invalid email format: $email" }
        require(name.isNotBlank()) { "User name cannot be blank" }
        // provider is enum, cannot be null
        profilePictureUrl?.let { url ->
            require(android.util.Patterns.WEB_URL.matcher(url).matches()) { "Invalid profile picture URL: $url" }
        }
    }
}
