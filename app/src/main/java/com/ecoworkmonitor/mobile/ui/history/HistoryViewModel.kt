package com.ecoworkmonitor.mobile.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoworkmonitor.mobile.domain.model.Alert
import com.ecoworkmonitor.mobile.domain.usecase.ClearAlertsUseCase
import com.ecoworkmonitor.mobile.domain.usecase.GetAlertsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getAlertsUseCase: GetAlertsUseCase,
    private val clearAlertsUseCase: ClearAlertsUseCase
) : ViewModel() {

    val alerts: StateFlow<List<Alert>> = getAlertsUseCase()
        .map { result ->
            if (result is com.ecoworkmonitor.mobile.core.common.Result.Success) {
                result.data
            } else {
                emptyList()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun clearHistory() {
        viewModelScope.launch {
            clearAlertsUseCase().collect {
                // Handle result if needed
            }
        }
    }
}
