package com.ecoworkmonitor.mobile.domain.usecase

import com.ecoworkmonitor.mobile.core.common.Result
import com.ecoworkmonitor.mobile.domain.model.Alert
import com.ecoworkmonitor.mobile.domain.repository.IAlertRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlertsUseCase @Inject constructor(
    private val alertRepository: IAlertRepository
) {
    operator fun invoke(): Flow<Result<List<Alert>>> {
        return alertRepository.getAllAlerts()
    }
}
