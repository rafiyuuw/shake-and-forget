package com.example.shake_and_forget.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import com.example.shake_and_forget.ui.theme.CoralPink
import com.example.shake_and_forget.ui.theme.ElectricGrape
import com.example.shake_and_forget.ui.theme.MintGreen
import com.example.shake_and_forget.ui.theme.SkyBlue
import com.example.shake_and_forget.ui.theme.SunnyYellow
import kotlin.random.Random

private data class ConfettiParticle(
    val xRatio: Float,
    val initialYRatio: Float,
    val speedY: Float,
    val speedX: Float,
    val size: Float,
    val color: Color
)

@Composable
fun ConfettiEffect(
    modifier: Modifier = Modifier,
    particleCount: Int = 35
) {
    val progress = remember { Animatable(0f) }
    val particles = remember {
        val colors = listOf(CoralPink, SunnyYellow, MintGreen, ElectricGrape, SkyBlue)
        List(particleCount) {
            ConfettiParticle(
                xRatio = Random.nextFloat(),
                initialYRatio = Random.nextFloat() * 0.3f,
                speedY = Random.nextFloat() * 0.8f + 0.5f,
                speedX = (Random.nextFloat() - 0.5f) * 0.4f,
                size = Random.nextFloat() * 12f + 8f,
                color = colors.random()
            )
        }
    }

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1200, easing = LinearEasing)
        )
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        particles.forEach { p ->
            val currentY = (p.initialYRatio + progress.value * p.speedY) * canvasHeight
            val currentX = (p.xRatio + progress.value * p.speedX) * canvasWidth
            val alpha = (1f - progress.value).coerceIn(0f, 1f)

            drawRect(
                color = p.color.copy(alpha = alpha),
                topLeft = Offset(currentX, currentY),
                size = Size(p.size, p.size * 0.6f)
            )
        }
    }
}
