package com.example.shake_and_forget.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shake_and_forget.data.preferences.SettingsRepository
import com.example.shake_and_forget.data.preferences.UserSettings
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val userSettings: StateFlow<UserSettings> = settingsRepository.settingsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserSettings()
    )

    fun updateShakeSensitivity(sensitivity: Float) {
        viewModelScope.launch {
            settingsRepository.updateShakeSensitivity(sensitivity)
        }
    }

    fun updateShakeEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateShakeEnabled(enabled)
        }
    }

    fun updateSoundEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateSoundEnabled(enabled)
        }
    }

    fun updateHapticEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateHapticEnabled(enabled)
        }
    }
}
