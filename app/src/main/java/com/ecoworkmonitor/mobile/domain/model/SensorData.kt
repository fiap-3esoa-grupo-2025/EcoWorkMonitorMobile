package com.ecoworkmonitor.mobile.domain.model

data class SensorData(
    val temperature: Float,
    val noiseLevel: Float,
    val luminosity: Float,
    val timestamp: Long
) {
    init {
        require(temperature in -50f..50f) { "Temperatura fora do range válido: $temperature" }
        require(noiseLevel >= 0f) { "Nível de ruído não pode ser negativo: $noiseLevel" }
        require(luminosity >= 0f) { "Luminosidade não pode ser negativa: $luminosity" }
        require(timestamp > 0) { "Timestamp inválido: $timestamp" }
    }
}
