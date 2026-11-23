package com.example.shake_and_forget.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shake_and_forget.data.local.entity.DecisionSetDetails
import com.example.shake_and_forget.data.preferences.SettingsRepository
import com.example.shake_and_forget.data.repository.DecisionRepository
import com.example.shake_and_forget.sensor.GyroscopeTracker
import com.example.shake_and_forget.sensor.ShakeDetector
import com.example.shake_and_forget.ui.util.HapticAndSoundHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.random.Random

data class ShakeDecisionUiState(
    val availableSets: List<DecisionSetDetails> = emptyList(),
    val selectedSet: DecisionSetDetails? = null,
    val highlightedOptionIndex: Int = 0,
    val isShaking: Boolean = false,
    val decisionResult: String? = null,
    val isShakeSensorAvailable: Boolean = true,
    val isGyroAvailable: Boolean = true,
    val isCommitted: Boolean = false
)

class ShakeDecisionViewModel(
    private val repository: DecisionRepository,
    private val settingsRepository: SettingsRepository,
    val shakeDetector: ShakeDetector,
    val gyroscopeTracker: GyroscopeTracker,
    private val hapticHelper: HapticAndSoundHelper? = null
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ShakeDecisionUiState(
            isShakeSensorAvailable = shakeDetector.isAvailable,
            isGyroAvailable = gyroscopeTracker.isAvailable
        )
    )
    val uiState: StateFlow<ShakeDecisionUiState> = _uiState.asStateFlow()

    private var soundEnabled = true
    private var hapticEnabled = true
    private var shakeJob: Job? = null

    init {
        viewModelScope.launch {
            repository.getAllDecisionSetsDetails().collectLatest { sets ->
                _uiState.value = _uiState.value.copy(
                    availableSets = sets,
                    selectedSet = _uiState.value.selectedSet ?: sets.firstOrNull()
                )
            }
        }

        viewModelScope.launch {
            settingsRepository.settingsFlow.collectLatest { settings ->
                shakeDetector.thresholdGForce = settings.shakeSensitivityGForce
                soundEnabled = settings.soundEnabled
                hapticEnabled = settings.hapticEnabled
            }
        }

        // Listen for shake events
        viewModelScope.launch {
            shakeDetector.shakeEvents.collect {
                triggerShakeRoll()
            }
        }

        // Listen for gyroscope tilt data to highlight options
        viewModelScope.launch {
            gyroscopeTracker.gyroData.collect { gyro ->
                val currentSet = _uiState.value.selectedSet ?: return@collect
                val options = currentSet.options
                if (options.isNotEmpty() && !_uiState.value.isShaking) {
                    val roll = gyro.roll
                    val absRoll = kotlin.math.abs(roll)
                    if (absRoll > 0.4f) {
                        val maxIndex = options.size - 1
                        val newIndex = if (roll > 0) {
                            (_uiState.value.highlightedOptionIndex + 1).coerceAtMost(maxIndex)
                        } else {
                            (_uiState.value.highlightedOptionIndex - 1).coerceAtLeast(0)
                        }
                        if (newIndex != _uiState.value.highlightedOptionIndex) {
                            hapticHelper?.playRollClick(soundEnabled, hapticEnabled)
                            _uiState.value = _uiState.value.copy(highlightedOptionIndex = newIndex)
                        }
                    }
                }
            }
        }
    }

    fun startSensors() {
        shakeDetector.startListening()
        gyroscopeTracker.startListening()
    }

    fun stopSensors() {
        shakeDetector.stopListening()
        gyroscopeTracker.stopListening()
    }

    fun selectDecisionSet(setId: Long) {
        val found = _uiState.value.availableSets.find { it.decisionSet.id == setId }
        _uiState.value = _uiState.value.copy(
            selectedSet = found,
            highlightedOptionIndex = 0,
            decisionResult = null,
            isCommitted = false
        )
    }

    fun triggerShakeRoll() {
        val currentSet = _uiState.value.selectedSet ?: return
        val options = currentSet.options
        if (options.isEmpty() || _uiState.value.isShaking) return

        shakeJob?.cancel()
        shakeJob = viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isShaking = true, decisionResult = null, isCommitted = false)

            val totalSteps = 10
            for (i in 0 until totalSteps) {
                hapticHelper?.playRollClick(soundEnabled, hapticEnabled)
                delay(70L + i * 12L)
                val randomIndex = Random.nextInt(options.size)
                _uiState.value = _uiState.value.copy(highlightedOptionIndex = randomIndex)
            }

            val finalIndex = Random.nextInt(options.size)
            val winner = options[finalIndex].title

            hapticHelper?.playSuccessCelebration(soundEnabled, hapticEnabled)

            _uiState.value = _uiState.value.copy(
                isShaking = false,
                highlightedOptionIndex = finalIndex,
                decisionResult = winner
            )
        }
    }

    fun commitDecision() {
        val currentSet = _uiState.value.selectedSet ?: return
        val result = _uiState.value.decisionResult ?: return

        viewModelScope.launch {
            repository.addHistoryEntry(currentSet.decisionSet.id, result)
            _uiState.value = _uiState.value.copy(isCommitted = true)
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopSensors()
    }
}
