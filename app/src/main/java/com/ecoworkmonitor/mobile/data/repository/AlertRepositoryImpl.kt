package com.ecoworkmonitor.mobile.data.repository

import com.ecoworkmonitor.mobile.core.common.Result
import com.ecoworkmonitor.mobile.data.local.dao.AlertDao
import com.ecoworkmonitor.mobile.data.local.entity.AlertEntity
import com.ecoworkmonitor.mobile.domain.model.Alert
import com.ecoworkmonitor.mobile.domain.model.DomainError
import com.ecoworkmonitor.mobile.domain.repository.IAlertRepository
import com.ecoworkmonitor.mobile.core.common.safeCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class AlertRepositoryImpl @Inject constructor(
    private val alertDao: AlertDao
) : IAlertRepository {

    override fun getAllAlerts(): Flow<Result<List<Alert>>> = safeCall {
        alertDao.getAllAlerts()
            .map { entities ->
                entities.map { it.toDomain() }
            }
            .first()
    }
    override fun insertAlert(alert: Alert): Flow<Result<Unit>> = safeCall {
        alertDao.insertAlert(AlertEntity.fromDomain(alert))
    }

    override fun clearAlerts(): Flow<Result<Unit>> = safeCall {
        alertDao.clearAlerts()
    }
}
