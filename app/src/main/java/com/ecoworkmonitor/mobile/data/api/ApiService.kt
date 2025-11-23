package com.ecoworkmonitor.mobile.data.api

import com.ecoworkmonitor.mobile.data.model.SensorDataDto

interface ApiService {
    suspend fun getSensorData(): SensorDataDto
}
