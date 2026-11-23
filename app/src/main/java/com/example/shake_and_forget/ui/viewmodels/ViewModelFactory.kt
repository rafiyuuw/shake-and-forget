package com.example.shake_and_forget.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shake_and_forget.data.local.AppDatabase
import com.example.shake_and_forget.data.preferences.SettingsRepositoryImpl
import com.example.shake_and_forget.data.repository.DecisionRepositoryImpl
import com.example.shake_and_forget.sensor.GyroscopeTracker
import com.example.shake_and_forget.sensor.ShakeDetector
import com.example.shake_and_forget.ui.util.HapticAndSoundHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class ViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    private val applicationScope = CoroutineScope(Dispatchers.IO)
    private val database = AppDatabase.getDatabase(context, applicationScope)
    private val decisionRepository = DecisionRepositoryImpl(
        categoryDao = database.categoryDao(),
        decisionSetDao = database.decisionSetDao(),
        optionDao = database.optionDao(),
        historyDao = database.historyDao()
    )
    private val settingsRepository = SettingsRepositoryImpl(context)

    private val shakeDetector = ShakeDetector(context)
    private val gyroscopeTracker = GyroscopeTracker(context)
    private val hapticHelper = HapticAndSoundHelper(context)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(decisionRepository) as T
            }
            modelClass.isAssignableFrom(QuickDecisionViewModel::class.java) -> {
                QuickDecisionViewModel(decisionRepository) as T
            }
            modelClass.isAssignableFrom(DecisionListViewModel::class.java) -> {
                DecisionListViewModel(decisionRepository) as T
            }
            modelClass.isAssignableFrom(ShakeDecisionViewModel::class.java) -> {
                ShakeDecisionViewModel(
                    repository = decisionRepository,
                    settingsRepository = settingsRepository,
                    shakeDetector = shakeDetector,
                    gyroscopeTracker = gyroscopeTracker,
                    hapticHelper = hapticHelper
                ) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(decisionRepository) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(settingsRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
