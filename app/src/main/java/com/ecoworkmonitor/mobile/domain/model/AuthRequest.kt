package com.ecoworkmonitor.mobile.domain.model

sealed class AuthRequest {
    abstract fun validate(): String?

    data class Email(
        val email: String,
        val password: String
    ) : AuthRequest() {
        override fun validate(): String? {
            if (email.isBlank() || password.isBlank()) {
                return "Email e senha são obrigatórios"
            }
            return null
        }
    }

    data class Provider(
        val provider: AuthProvider,
        val token: String
    ) : AuthRequest() {
        override fun validate(): String? {
            if (token.isBlank()) {
                return "Token do provedor não pode estar vazio"
            }
            if (provider == AuthProvider.EMAIL) {
                return "Use AuthRequest.Email para login com email/senha"
            }
            return null
        }
    }

    data class RefreshToken(
        val token: String
    ) : AuthRequest() {
        override fun validate(): String? {
            if (token.isBlank()) {
                return "Refresh token não pode estar vazio"
            }
            return null
        }
    }
}
