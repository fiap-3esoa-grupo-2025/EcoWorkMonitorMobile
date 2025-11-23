package com.ecoworkmonitor.mobile.data.api

import com.ecoworkmonitor.mobile.data.model.SensorDataDto
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

class FakeApiService @Inject constructor() : ApiService {
    override suspend fun getSensorData(): SensorDataDto {
        delay(1000) // Simulate network delay
        return SensorDataDto(
            temp = 20f + Random.nextFloat() * 10f, // 20-30 degrees
            noise = 30f + Random.nextFloat() * 40f, // 30-70 dB
            light = 100f + Random.nextFloat() * 400f, // 100-500 lux
            timestamp = System.currentTimeMillis()
        )
    }
}
