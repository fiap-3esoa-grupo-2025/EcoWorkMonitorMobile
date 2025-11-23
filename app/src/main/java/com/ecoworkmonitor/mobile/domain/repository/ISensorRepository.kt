package com.ecoworkmonitor.mobile.domain.repository

import com.ecoworkmonitor.mobile.core.common.Result
import com.ecoworkmonitor.mobile.domain.model.SensorData
import kotlinx.coroutines.flow.Flow

interface ISensorRepository {
    fun getSummary(): Flow<Result<SensorData>>
}
