package com.example.shake_and_forget.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = ElectricGrape,
    onPrimary = PureWhite,
    primaryContainer = ElectricGrape,
    onPrimaryContainer = PureWhite,
    secondary = CoralPink,
    onSecondary = PureWhite,
    secondaryContainer = CoralPink,
    onSecondaryContainer = PrimaryInk,
    tertiary = SunnyYellow,
    onTertiary = PrimaryInk,
    tertiaryContainer = SunnyYellow,
    onTertiaryContainer = PrimaryInk,
    background = MilkCream,
    onBackground = PrimaryInk,
    surface = PureWhite,
    onSurface = PrimaryInk,
    surfaceVariant = SurfaceContainerHigh,
    onSurfaceVariant = SecondaryInk,
    outline = SecondaryInk,
    outlineVariant = OutlineBorder
)

@Composable
fun ShakeAndForgetTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = MilkCream.toArgb()
            window.navigationBarColor = PureWhite.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = true
                isAppearanceLightNavigationBars = true
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
