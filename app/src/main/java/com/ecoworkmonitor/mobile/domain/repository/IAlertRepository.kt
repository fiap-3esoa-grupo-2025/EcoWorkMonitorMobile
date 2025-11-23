package com.ecoworkmonitor.mobile.domain.repository

import com.ecoworkmonitor.mobile.core.common.Result
import com.ecoworkmonitor.mobile.domain.model.Alert
import kotlinx.coroutines.flow.Flow

interface IAlertRepository {
    fun getAllAlerts(): Flow<Result<List<Alert>>>
    fun insertAlert(alert: Alert): Flow<Result<Unit>>
    fun clearAlerts(): Flow<Result<Unit>>
}
