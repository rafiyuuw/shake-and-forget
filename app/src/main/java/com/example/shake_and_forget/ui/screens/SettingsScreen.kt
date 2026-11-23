package com.example.shake_and_forget.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shake_and_forget.ui.theme.DesignTokens
import com.example.shake_and_forget.ui.theme.ElectricGrape
import com.example.shake_and_forget.ui.theme.MilkCream
import com.example.shake_and_forget.ui.theme.MintGreen
import com.example.shake_and_forget.ui.theme.PrimaryInk
import com.example.shake_and_forget.ui.theme.PureWhite
import com.example.shake_and_forget.ui.theme.SecondaryInk
import com.example.shake_and_forget.ui.viewmodels.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onNavigateBack: () -> Unit = {}
) {
    val settings by viewModel.userSettings.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MilkCream)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = PrimaryInk
                )
            }
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineLarge,
                color = PrimaryInk
            )
        }

        // Shake Sensitivity Control
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(2.dp, DesignTokens.CardShape),
            shape = DesignTokens.CardShape,
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            border = DesignTokens.BorderStyle
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Shake Sensitivity Threshold",
                    style = MaterialTheme.typography.titleMedium,
                    color = PrimaryInk
                )

                val sensitivityLabel = when {
                    settings.shakeSensitivityGForce <= 10.0f -> "High Sensitivity (Light Shake)"
                    settings.shakeSensitivityGForce <= 15.0f -> "Medium Sensitivity (Normal Shake)"
                    else -> "Low Sensitivity (Firm Shake)"
                }

                Text(
                    text = sensitivityLabel,
                    style = MaterialTheme.typography.bodyMedium,
                    color = ElectricGrape
                )

                Slider(
                    value = settings.shakeSensitivityGForce,
                    onValueChange = { viewModel.updateShakeSensitivity(it) },
                    valueRange = 8.0f..22.0f,
                    steps = 2,
                    colors = SliderDefaults.colors(
                        thumbColor = ElectricGrape,
                        activeTrackColor = ElectricGrape
                    )
                )
            }
        }

        // Toggles Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(2.dp, DesignTokens.CardShape),
            shape = DesignTokens.CardShape,
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            border = DesignTokens.BorderStyle
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SettingToggleRow(
                    title = "Enable Shake Detection",
                    subtitle = "Trigger random choice when physical shake is detected",
                    checked = settings.shakeEnabled,
                    onCheckedChange = { viewModel.updateShakeEnabled(it) }
                )

                SettingToggleRow(
                    title = "Sound Effects",
                    subtitle = "Play playful audio cues during decision rolls",
                    checked = settings.soundEnabled,
                    onCheckedChange = { viewModel.updateSoundEnabled(it) }
                )

                SettingToggleRow(
                    title = "Haptic Feedback",
                    subtitle = "Vibrate device when decision is selected",
                    checked = settings.hapticEnabled,
                    onCheckedChange = { viewModel.updateHapticEnabled(it) }
                )
            }
        }

        // About Application Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(2.dp, DesignTokens.CardShape),
            shape = DesignTokens.CardShape,
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            border = DesignTokens.BorderStyle
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "SHAKE & FORGET",
                    style = MaterialTheme.typography.titleMedium,
                    color = ElectricGrape
                )
                Text(
                    text = "Version 1.0 (Build 1)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PrimaryInk
                )
                Text(
                    text = "Stop overthinking. Let it shake.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SecondaryInk
                )
            }
        }
    }
}

@Composable
private fun SettingToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = PrimaryInk
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = SecondaryInk
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = PureWhite,
                checkedTrackColor = MintGreen
            )
        )
    }
}
