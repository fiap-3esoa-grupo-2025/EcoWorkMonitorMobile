package com.ecoworkmonitor.mobile.data.model

import com.ecoworkmonitor.mobile.domain.model.SensorData

data class SensorDataDto(
    val temp: Float,
    val noise: Float,
    val light: Float,
    val timestamp: Long
) {
    fun toDomain(): SensorData {
        return SensorData(
            temperature = temp,
            noiseLevel = noise,
            luminosity = light,
            timestamp = timestamp
        )
    }
}
