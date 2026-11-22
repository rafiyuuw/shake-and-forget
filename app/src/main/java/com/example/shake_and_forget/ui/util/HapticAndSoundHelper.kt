package com.example.shake_and_forget.ui.util

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

class HapticAndSoundHelper(private val context: Context) {

    private var toneGenerator: ToneGenerator? = try {
        ToneGenerator(AudioManager.STREAM_MUSIC, 80)
    } catch (_: Exception) {
        null
    }

    fun playRollClick(soundEnabled: Boolean, hapticEnabled: Boolean) {
        if (soundEnabled) {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 40)
        }
        if (hapticEnabled) {
            triggerVibration(context, 30L)
        }
    }

    fun playSuccessCelebration(soundEnabled: Boolean, hapticEnabled: Boolean) {
        if (soundEnabled) {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_ACK, 120)
        }
        if (hapticEnabled) {
            triggerVibration(context, 100L)
        }
    }

    private fun triggerVibration(context: Context, durationMs: Long) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.vibrate(
                    CombinedVibration.createParallel(
                        VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE)
                    )
                )
            } else {
                @Suppress("DEPRECATION")
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                vibrator?.vibrate(VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE))
            }
        } catch (_: Exception) {}
    }
}
