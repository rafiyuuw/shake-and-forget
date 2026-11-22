package com.example.shake_and_forget.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings_prefs")

data class UserSettings(
    val shakeSensitivityGForce: Float = 13.0f,
    val shakeEnabled: Boolean = true,
    val soundEnabled: Boolean = true,
    val hapticEnabled: Boolean = true
)

interface SettingsRepository {
    val settingsFlow: Flow<UserSettings>
    suspend fun updateShakeSensitivity(sensitivity: Float)
    suspend fun updateShakeEnabled(enabled: Boolean)
    suspend fun updateSoundEnabled(enabled: Boolean)
    suspend fun updateHapticEnabled(enabled: Boolean)
}

class SettingsRepositoryImpl(private val context: Context) : SettingsRepository {

    private object PreferencesKeys {
        val SHAKE_SENSITIVITY = floatPreferencesKey("shake_sensitivity")
        val SHAKE_ENABLED = booleanPreferencesKey("shake_enabled")
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        val HAPTIC_ENABLED = booleanPreferencesKey("haptic_enabled")
    }

    override val settingsFlow: Flow<UserSettings> = context.dataStore.data.map { prefs ->
        UserSettings(
            shakeSensitivityGForce = prefs[PreferencesKeys.SHAKE_SENSITIVITY] ?: 13.0f,
            shakeEnabled = prefs[PreferencesKeys.SHAKE_ENABLED] ?: true,
            soundEnabled = prefs[PreferencesKeys.SOUND_ENABLED] ?: true,
            hapticEnabled = prefs[PreferencesKeys.HAPTIC_ENABLED] ?: true
        )
    }

    override suspend fun updateShakeSensitivity(sensitivity: Float) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.SHAKE_SENSITIVITY] = sensitivity
        }
    }

    override suspend fun updateShakeEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.SHAKE_ENABLED] = enabled
        }
    }

    override suspend fun updateSoundEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.SOUND_ENABLED] = enabled
        }
    }

    override suspend fun updateHapticEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.HAPTIC_ENABLED] = enabled
        }
    }
}
