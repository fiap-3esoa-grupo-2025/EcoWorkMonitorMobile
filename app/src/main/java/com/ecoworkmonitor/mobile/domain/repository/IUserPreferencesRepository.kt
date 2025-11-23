package com.ecoworkmonitor.mobile.domain.repository

import kotlinx.coroutines.flow.Flow

interface IUserPreferencesRepository {
    val isDarkTheme: Flow<Boolean>
    val tempThresholdMax: Flow<Float>
    val noiseThresholdMax: Flow<Float>
    val lightThresholdMin: Flow<Float>
    val accessToken: Flow<String?>
    val refreshToken: Flow<String?>
    
    suspend fun setDarkTheme(isDark: Boolean)
    suspend fun setTempThresholdMax(value: Float)
    suspend fun setNoiseThresholdMax(value: Float)
    suspend fun setLightThresholdMin(value: Float)
    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun clearTokens()
}
