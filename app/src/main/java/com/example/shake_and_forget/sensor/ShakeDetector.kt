package com.example.shake_and_forget.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.math.sqrt

class ShakeDetector(context: Context) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    val isAvailable: Boolean = accelerometer != null

    private val _shakeEvents = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val shakeEvents: SharedFlow<Unit> = _shakeEvents.asSharedFlow()

    // Threshold in m/s^2 above gravity
    var thresholdGForce: Float = 13.0f
    var cooldownMs: Long = 1000L

    private var lastShakeTimestamp: Long = 0L

    fun startListening() {
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null || event.sensor.type != Sensor.TYPE_ACCELEROMETER) return

        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val gX = x / SensorManager.GRAVITY_EARTH
        val gY = y / SensorManager.GRAVITY_EARTH
        val gZ = z / SensorManager.GRAVITY_EARTH

        // Total G-force magnitude minus 1G (earth gravity)
        val gForce = sqrt(gX * gX + gY * gY + gZ * gZ) - 1.0f

        val currentTime = System.currentTimeMillis()
        if (gForce * SensorManager.GRAVITY_EARTH >= thresholdGForce) {
            if (currentTime - lastShakeTimestamp >= cooldownMs) {
                lastShakeTimestamp = currentTime
                _shakeEvents.tryEmit(Unit)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
