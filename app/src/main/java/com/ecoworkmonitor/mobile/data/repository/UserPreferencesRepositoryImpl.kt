package com.ecoworkmonitor.mobile.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.ecoworkmonitor.mobile.domain.repository.IUserPreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class UserPreferencesRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : IUserPreferencesRepository {
    private val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
    private val TEMP_THRESHOLD_MAX = androidx.datastore.preferences.core.floatPreferencesKey("temp_threshold_max")
    private val NOISE_THRESHOLD_MAX = androidx.datastore.preferences.core.floatPreferencesKey("noise_threshold_max")
    private val LIGHT_THRESHOLD_MIN = androidx.datastore.preferences.core.floatPreferencesKey("light_threshold_min")
    private val ACCESS_TOKEN = androidx.datastore.preferences.core.stringPreferencesKey("access_token")
    private val REFRESH_TOKEN = androidx.datastore.preferences.core.stringPreferencesKey("refresh_token")

    override val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_DARK_THEME] ?: false
        }

    override suspend fun setDarkTheme(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_THEME] = isDark
        }
    }

    override val tempThresholdMax: Flow<Float> = context.dataStore.data.map { it[TEMP_THRESHOLD_MAX] ?: 30.0f }
    override val noiseThresholdMax: Flow<Float> = context.dataStore.data.map { it[NOISE_THRESHOLD_MAX] ?: 80.0f }
    override val lightThresholdMin: Flow<Float> = context.dataStore.data.map { it[LIGHT_THRESHOLD_MIN] ?: 200.0f }

    override suspend fun setTempThresholdMax(value: Float) {
        context.dataStore.edit { it[TEMP_THRESHOLD_MAX] = value }
    }

    override suspend fun setNoiseThresholdMax(value: Float) {
        context.dataStore.edit { it[NOISE_THRESHOLD_MAX] = value }
    }

    override suspend fun setLightThresholdMin(value: Float) {
        context.dataStore.edit { it[LIGHT_THRESHOLD_MIN] = value }
    }

    override val accessToken: Flow<String?> = context.dataStore.data.map { it[ACCESS_TOKEN] }
    override val refreshToken: Flow<String?> = context.dataStore.data.map { it[REFRESH_TOKEN] }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken
            preferences[REFRESH_TOKEN] = refreshToken
        }
    }

    override suspend fun clearTokens() {
        context.dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN)
            preferences.remove(REFRESH_TOKEN)
        }
    }
}
