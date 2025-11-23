package com.ecoworkmonitor.mobile.ui.login

import com.ecoworkmonitor.mobile.domain.model.AuthProvider

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoggedIn: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null
)

sealed class LoginEvent {
    data class OnEmailChanged(val email: String) : LoginEvent()
    data class OnPasswordChanged(val password: String) : LoginEvent()
    object OnLoginClicked : LoginEvent()
    data class OnProviderLoginClicked(val provider: AuthProvider) : LoginEvent()
    object OnErrorDismissed : LoginEvent()
}
