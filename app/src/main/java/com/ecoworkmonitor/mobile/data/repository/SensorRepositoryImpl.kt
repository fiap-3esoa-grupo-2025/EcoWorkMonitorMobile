package com.ecoworkmonitor.mobile.data.repository

import com.ecoworkmonitor.mobile.core.common.Result
import com.ecoworkmonitor.mobile.core.common.safeCall
import com.ecoworkmonitor.mobile.data.api.ApiService
import com.ecoworkmonitor.mobile.domain.model.SensorData
import com.ecoworkmonitor.mobile.domain.repository.ISensorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SensorRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ISensorRepository {

    override fun getSummary(): Flow<Result<SensorData>> = safeCall {
        apiService.getSensorData().toDomain()
    }
}
