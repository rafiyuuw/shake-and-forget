package com.example.shake_and_forget.ui.util

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Build
import android.os.CombinedVibration
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlin.math.sin

class HapticAndSoundHelper(private val context: Context) {

    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager

    fun playRollClick(soundEnabled: Boolean = true, hapticEnabled: Boolean = true) {
        if (soundEnabled) {
            audioManager?.playSoundEffect(AudioManager.FX_KEY_CLICK, 1.0f)
            playPcmTone(frequency = 600, durationMs = 40)
        }
        if (hapticEnabled) {
            triggerVibration(context, 35L)
        }
    }

    fun playSuccessCelebration(soundEnabled: Boolean = true, hapticEnabled: Boolean = true) {
        if (soundEnabled) {
            audioManager?.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD, 1.0f)
            playPcmTone(frequency = 880, durationMs = 120)
        }
        if (hapticEnabled) {
            triggerVibration(context, 100L)
        }
    }

    private fun playPcmTone(frequency: Int, durationMs: Int) {
        try {
            val sampleRate = 22050
            val numSamples = durationMs * sampleRate / 1000
            val sample = DoubleArray(numSamples)
            val generatedSnd = ByteArray(2 * numSamples)

            for (i in 0 until numSamples) {
                sample[i] = sin(2.0 * Math.PI * i.toDouble() / (sampleRate.toDouble() / frequency.toDouble()))
            }

            var idx = 0
            for (dVal in sample) {
                val valShort = (dVal * 32767).toInt().toShort()
                generatedSnd[idx++] = (valShort.toInt() and 0x00ff).toByte()
                generatedSnd[idx++] = (valShort.toInt() and 0xff00 ushr 8).toByte()
            }

            val audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(generatedSnd.size)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            audioTrack.write(generatedSnd, 0, generatedSnd.size)
            audioTrack.play()

            Handler(Looper.getMainLooper()).postDelayed({
                try {
                    audioTrack.stop()
                    audioTrack.release()
                } catch (_: Exception) {}
            }, (durationMs + 100).toLong())

        } catch (_: Exception) {}
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
