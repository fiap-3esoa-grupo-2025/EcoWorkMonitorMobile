package com.ecoworkmonitor.mobile.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoworkmonitor.mobile.domain.repository.IUserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: IUserPreferencesRepository,
    private val logoutUseCase: com.ecoworkmonitor.mobile.domain.usecase.LogoutUseCase
) : ViewModel() {

    private val _logoutState = MutableStateFlow<LogoutState>(LogoutState.Idle)
    val logoutState: StateFlow<LogoutState> = _logoutState.asStateFlow()

    val isDarkTheme: StateFlow<Boolean> = userPreferencesRepository.isDarkTheme
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    val tempThreshold: StateFlow<Float> = userPreferencesRepository.tempThresholdMax
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 30f
        )

    val noiseThreshold: StateFlow<Float> = userPreferencesRepository.noiseThresholdMax
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 80f
        )

    fun toggleTheme(isDark: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setDarkTheme(isDark)
        }
    }

    fun updateTempThreshold(value: Float) {
        viewModelScope.launch {
            userPreferencesRepository.setTempThresholdMax(value)
        }
    }

    fun updateNoiseThreshold(value: Float) {
        viewModelScope.launch {
            userPreferencesRepository.setNoiseThresholdMax(value)
        }
    }

    fun logout() {
        viewModelScope.launch {
            _logoutState.value = LogoutState.Loading
            logoutUseCase().collect { result ->
                when (result) {
                    is com.ecoworkmonitor.mobile.core.common.Result.Success -> {
                        _logoutState.value = LogoutState.Success
                    }
                    is com.ecoworkmonitor.mobile.core.common.Result.Error -> {
                        _logoutState.value = LogoutState.Error(
                            result.error.toString()
                        )
                    }
                    is com.ecoworkmonitor.mobile.core.common.Result.Loading -> {
                        _logoutState.value = LogoutState.Loading
                    }
                }
            }
        }
    }

    fun resetLogoutState() {
        _logoutState.value = LogoutState.Idle
    }
}

sealed class LogoutState {
    object Idle : LogoutState()
    object Loading : LogoutState()
    object Success : LogoutState()
    data class Error(val message: String) : LogoutState()
}
