package com.example.shake_and_forget.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class GyroscopeData(
    val pitch: Float = 0f, // Rotation around X-axis
    val roll: Float = 0f,  // Rotation around Y-axis
    val yaw: Float = 0f    // Rotation around Z-axis
)

class GyroscopeTracker(context: Context) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    val isAvailable: Boolean = gyroscope != null

    private val _gyroData = MutableStateFlow(GyroscopeData())
    val gyroData: StateFlow<GyroscopeData> = _gyroData.asStateFlow()

    // Filter alpha for low-pass smoothing (0.1f = very smooth, 0.9f = sharp)
    private val alpha = 0.2f
    private var currentPitch = 0f
    private var currentRoll = 0f
    private var currentYaw = 0f

    fun startListening() {
        gyroscope?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null || event.sensor.type != Sensor.TYPE_GYROSCOPE) return

        val pitchRate = event.values[0]
        val rollRate = event.values[1]
        val yawRate = event.values[2]

        // Low-pass filter smoothing
        currentPitch = currentPitch + alpha * (pitchRate - currentPitch)
        currentRoll = currentRoll + alpha * (rollRate - currentRoll)
        currentYaw = currentYaw + alpha * (yawRate - currentYaw)

        _gyroData.value = GyroscopeData(
            pitch = currentPitch,
            roll = currentRoll,
            yaw = currentYaw
        )
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
