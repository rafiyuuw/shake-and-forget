package com.example.shake_and_forget.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shake_and_forget.ui.components.ConfettiEffect
import com.example.shake_and_forget.ui.theme.CoralPink
import com.example.shake_and_forget.ui.theme.DesignTokens
import com.example.shake_and_forget.ui.theme.ElectricGrape
import com.example.shake_and_forget.ui.theme.MilkCream
import com.example.shake_and_forget.ui.theme.MintGreen
import com.example.shake_and_forget.ui.theme.PrimaryInk
import com.example.shake_and_forget.ui.theme.PureWhite
import com.example.shake_and_forget.ui.theme.SecondaryInk
import com.example.shake_and_forget.ui.theme.SunnyYellow
import com.example.shake_and_forget.ui.viewmodels.ShakeDecisionViewModel

@Composable
fun ShakeToDecideScreen(
    viewModel: ShakeDecisionViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        viewModel.startSensors()
        onDispose {
            viewModel.stopSensors()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MilkCream)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Shake to Decide",
                style = MaterialTheme.typography.headlineLarge,
                color = PrimaryInk
            )

            // Select Decision List
            Text(
                text = "Active Decision List",
                style = MaterialTheme.typography.titleMedium,
                color = PrimaryInk
            )

            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(uiState.availableSets) { setDetails ->
                    val isSelected = uiState.selectedSet?.decisionSet?.id == setDetails.decisionSet.id
                    Box(
                        modifier = Modifier
                            .background(
                                if (isSelected) ElectricGrape else PureWhite,
                                shape = DesignTokens.PillShape
                            )
                            .border(DesignTokens.BorderStyle, shape = DesignTokens.PillShape)
                            .clickable { viewModel.selectDecisionSet(setDetails.decisionSet.id) }
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = setDetails.decisionSet.title,
                            style = MaterialTheme.typography.labelLarge,
                            color = if (isSelected) PureWhite else PrimaryInk
                        )
                    }
                }
            }

            // Active Options Tiles (with Gyroscope Tilt Highlight)
            uiState.selectedSet?.let { currentSet ->
                Text(
                    text = "Tilt or Shake to Select (${currentSet.options.size} Options)",
                    style = MaterialTheme.typography.titleMedium,
                    color = PrimaryInk
                )

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    currentSet.options.forEachIndexed { index, option ->
                        val isHighlighted = index == uiState.highlightedOptionIndex
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(if (isHighlighted) 8.dp else 2.dp, DesignTokens.CardShape),
                            shape = DesignTokens.CardShape,
                            colors = CardDefaults.cardColors(
                                containerColor = if (isHighlighted) SunnyYellow else PureWhite
                            ),
                            border = DesignTokens.BorderStyle
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(18.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = option.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = PrimaryInk
                                )
                                if (isHighlighted) {
                                    Text(
                                        text = "★ HIGHLIGHTED",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = ElectricGrape
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Sensor Status Banner & Manual Shake Trigger
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, DesignTokens.CardShape),
                shape = DesignTokens.CardShape,
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = DesignTokens.BorderStyle
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Vibration,
                            contentDescription = null,
                            tint = ElectricGrape,
                            modifier = Modifier.size(28.dp)
                        )
                        Text(
                            text = if (uiState.isShaking) "SHAKING IN PROGRESS..." else "SHAKE YOUR PHONE NOW!",
                            style = MaterialTheme.typography.titleMedium,
                            color = PrimaryInk
                        )
                    }

                    Text(
                        text = if (uiState.isShakeSensorAvailable)
                            "Accelerometer motion sensor active. Give your phone a firm shake!"
                        else
                            "Sensor unavailable on this device. Use manual shake button below.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SecondaryInk
                    )

                    Button(
                        onClick = { viewModel.triggerShakeRoll() },
                        enabled = !uiState.isShaking && uiState.selectedSet != null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .shadow(4.dp, DesignTokens.PillShape),
                        shape = DesignTokens.PillShape,
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricGrape, contentColor = PureWhite)
                    ) {
                        Icon(imageVector = Icons.Default.Vibration, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "TRIGGER MANUAL SHAKE", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }

            // Decision Result Overlay Card
            AnimatedVisibility(
                visible = uiState.decisionResult != null,
                enter = fadeIn(tween(300)) + scaleIn(tween(300))
            ) {
                uiState.decisionResult?.let { resultText ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(12.dp, DesignTokens.CardShape),
                        shape = DesignTokens.CardShape,
                        colors = CardDefaults.cardColors(containerColor = MintGreen),
                        border = DesignTokens.BorderStyle
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Celebration,
                                contentDescription = null,
                                tint = PrimaryInk,
                                modifier = Modifier.size(40.dp)
                            )
                            Text(
                                text = "FINAL VERDICT",
                                style = MaterialTheme.typography.labelSmall,
                                color = PrimaryInk
                            )
                            Text(
                                text = resultText,
                                style = MaterialTheme.typography.displayLarge,
                                color = PrimaryInk
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Button(
                                    onClick = { viewModel.triggerShakeRoll() },
                                    modifier = Modifier.weight(1f),
                                    shape = DesignTokens.PillShape,
                                    colors = ButtonDefaults.buttonColors(containerColor = CoralPink, contentColor = PureWhite)
                                ) {
                                    Text("SHAKE AGAIN")
                                }

                                Button(
                                    onClick = { viewModel.commitDecision() },
                                    enabled = !uiState.isCommitted,
                                    modifier = Modifier.weight(1f),
                                    shape = DesignTokens.PillShape,
                                    colors = ButtonDefaults.buttonColors(containerColor = ElectricGrape, contentColor = PureWhite)
                                ) {
                                    Icon(imageVector = Icons.Default.Check, contentDescription = null)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(if (uiState.isCommitted) "SAVED!" else "COMMIT")
                                }
                            }
                        }
                    }
                }
            }
        }

        // Confetti Particle Celebration Effect
        if (uiState.decisionResult != null) {
            ConfettiEffect()
        }
    }
}
