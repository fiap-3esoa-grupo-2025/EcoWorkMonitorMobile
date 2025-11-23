package com.ecoworkmonitor.mobile.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoworkmonitor.mobile.core.common.Result
import com.ecoworkmonitor.mobile.domain.model.AuthProvider
import com.ecoworkmonitor.mobile.domain.model.AuthRequest
import com.ecoworkmonitor.mobile.domain.model.AuthResponse
import com.ecoworkmonitor.mobile.domain.model.DomainError
import com.ecoworkmonitor.mobile.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    
    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChanged -> {
                _uiState.update { it.copy(email = event.email, emailError = null) }
            }
            is LoginEvent.OnPasswordChanged -> {
                _uiState.update { it.copy(password = event.password, passwordError = null) }
            }
            is LoginEvent.OnLoginClicked -> {
                performLogin()
            }
            is LoginEvent.OnProviderLoginClicked -> {
                performProviderLogin(event.provider)
            }
            is LoginEvent.OnErrorDismissed -> {
                _uiState.update { it.copy(errorMessage = null) }
            }
        }
    }
    
    private fun performLogin() {
        viewModelScope.launch {
            loginUseCase(
                AuthRequest.Email(
                    email = _uiState.value.email,
                    password = _uiState.value.password
                )
            ).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _uiState.update { 
                            it.copy(
                                isLoading = true, 
                                errorMessage = null,
                                emailError = null,
                                passwordError = null
                            ) 
                        }
                    }
                    is Result.Success -> {
                        // We only care about Login response here
                        if (result.data is AuthResponse.Login) {
                            _uiState.update { 
                                it.copy(
                                    isLoading = false, 
                                    isLoggedIn = true,
                                    errorMessage = null
                                ) 
                            }
                        }
                    }
                    is Result.Error -> {
                        val errorMessage = when (val error = result.error) {
                            is DomainError.NetworkError -> "Sem conexão com a internet. Verifique sua rede."
                            is DomainError.ServerError -> "Erro no servidor (${error.code}). Tente novamente mais tarde."
                            is DomainError.AuthError -> error.message ?: "Erro de autenticação."
                            is DomainError.ValidationError -> error.message
                            is DomainError.UnknownError -> "Erro desconhecido. Tente novamente."
                        }
                        _uiState.update { 
                            it.copy(
                                isLoading = false, 
                                errorMessage = errorMessage,
                                isLoggedIn = false
                            ) 
                        }
                    }
                }
            }
        }
    }
    
    private fun performProviderLogin(provider: AuthProvider) {
        viewModelScope.launch {
            // In a real app, this would trigger the OAuth flow
            // For simulation, we generate a fake token
            val fakeProviderToken = "provider_token_${UUID.randomUUID()}"
            
            loginUseCase(
                AuthRequest.Provider(
                    provider = provider,
                    token = fakeProviderToken
                )
            ).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _uiState.update { 
                            it.copy(
                                isLoading = true, 
                                errorMessage = null
                            ) 
                        }
                    }
                    is Result.Success -> {
                        if (result.data is AuthResponse.Login) {
                            _uiState.update { 
                                it.copy(
                                    isLoading = false, 
                                    isLoggedIn = true,
                                    errorMessage = null
                                ) 
                            }
                        }
                    }
                    is Result.Error -> {
                        val errorMessage = when (val error = result.error) {
                            is DomainError.NetworkError -> "Sem conexão com a internet. Verifique sua rede."
                            is DomainError.ServerError -> "Erro no servidor (${error.code}). Tente novamente mais tarde."
                            is DomainError.AuthError -> error.message ?: "Erro de autenticação."
                            is DomainError.ValidationError -> error.message
                            is DomainError.UnknownError -> "Erro desconhecido. Tente novamente."
                        }
                        _uiState.update { 
                            it.copy(
                                isLoading = false, 
                                errorMessage = errorMessage,
                                isLoggedIn = false
                            ) 
                        }
                    }
                }
            }
        }
    }
    
    fun resetLoginState() {
        _uiState.update { it.copy(isLoggedIn = false) }
    }
}
