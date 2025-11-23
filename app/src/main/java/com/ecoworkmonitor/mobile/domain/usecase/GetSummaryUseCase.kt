package com.ecoworkmonitor.mobile.domain.usecase

import com.ecoworkmonitor.mobile.core.common.Result
import com.ecoworkmonitor.mobile.domain.model.SensorData
import com.ecoworkmonitor.mobile.domain.repository.ISensorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSummaryUseCase @Inject constructor(
    private val repository: ISensorRepository
) {
    operator fun invoke(): Flow<Result<SensorData>> {
        return repository.getSummary()
    }
}
