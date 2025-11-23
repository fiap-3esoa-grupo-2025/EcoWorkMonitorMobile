package com.ecoworkmonitor.mobile.domain.usecase

import com.ecoworkmonitor.mobile.domain.model.Alert
import com.ecoworkmonitor.mobile.domain.model.AlertType
import com.ecoworkmonitor.mobile.domain.model.SensorData
import com.ecoworkmonitor.mobile.domain.repository.IAlertRepository
import com.ecoworkmonitor.mobile.domain.repository.IUserPreferencesRepository
import com.ecoworkmonitor.mobile.domain.service.NotificationService
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CheckAlertsUseCase @Inject constructor(
    private val userPreferencesRepository: IUserPreferencesRepository,
    private val alertRepository: IAlertRepository,
    private val notificationService: NotificationService
) {
    suspend operator fun invoke(sensorData: SensorData) {
        val tempMax = userPreferencesRepository.tempThresholdMax.first()
        val noiseMax = userPreferencesRepository.noiseThresholdMax.first()
        val lightMin = userPreferencesRepository.lightThresholdMin.first()

        if (sensorData.temperature > tempMax) {
            val message = "Temperatura alta detectada: ${sensorData.temperature}°C (Limite: $tempMax°C)"
            createAlert(AlertType.TEMPERATURE, sensorData.temperature, tempMax, message, 1)
        }

        if (sensorData.noiseLevel > noiseMax) {
            val message = "Ruído alto detectado: ${sensorData.noiseLevel}dB (Limite: $noiseMax dB)"
            createAlert(AlertType.NOISE, sensorData.noiseLevel, noiseMax, message, 2)
        }

        if (sensorData.luminosity < lightMin) {
            val message = "Luminosidade baixa: ${sensorData.luminosity}lx (Mínimo: $lightMin lx)"
            createAlert(AlertType.LUMINOSITY, sensorData.luminosity, lightMin, message, 3)
        }
    }

    private suspend fun createAlert(type: AlertType, value: Float, threshold: Float, message: String, notificationId: Int) {
        val alert = Alert(
            type = type,
            value = value,
            threshold = threshold,
            timestamp = System.currentTimeMillis(),
            message = message
        )
        alertRepository.insertAlert(alert).first()
        notificationService.showNotification("Alerta EcoWork", message, notificationId)
    }
}
