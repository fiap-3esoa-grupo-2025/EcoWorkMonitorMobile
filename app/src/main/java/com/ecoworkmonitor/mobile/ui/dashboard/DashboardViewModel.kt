package com.ecoworkmonitor.mobile.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoworkmonitor.mobile.core.common.Result
import com.ecoworkmonitor.mobile.domain.model.DomainError
import com.ecoworkmonitor.mobile.domain.model.SensorData
import com.ecoworkmonitor.mobile.domain.usecase.CheckAlertsUseCase
import com.ecoworkmonitor.mobile.domain.usecase.GetSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDashboardDataUseCase: GetSummaryUseCase,
    private val checkAlertsUseCase: CheckAlertsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            getDashboardDataUseCase().collect { result ->
                when (result) {
                    is Result.Loading -> _uiState.value = DashboardUiState.Loading
                    is Result.Success -> {
                        _uiState.value = DashboardUiState.Success(result.data)
                        checkAlertsUseCase(result.data)
                    }
                    is Result.Error -> {
                        val errorMessage = when (val error = result.error) {
                            is DomainError.NetworkError -> "Sem conexão com a internet. Verifique sua rede."
                            is DomainError.ServerError -> "Erro no servidor (${error.code}). Tente novamente mais tarde."
                            is DomainError.AuthError -> error.message ?: "Erro de autenticação."
                            is DomainError.ValidationError -> error.message
                            is DomainError.UnknownError -> "Erro ao carregar dados. Tente novamente."
                        }
                        _uiState.value = DashboardUiState.Error(errorMessage)
                    }
                }
            }
        }
    }
}

sealed class DashboardUiState {
    object Loading : DashboardUiState()
    data class Success(val data: SensorData) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}
