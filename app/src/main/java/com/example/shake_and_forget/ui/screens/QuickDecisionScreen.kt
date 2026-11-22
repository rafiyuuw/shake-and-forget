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
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shake_and_forget.ui.theme.CoralPink
import com.example.shake_and_forget.ui.theme.DesignTokens
import com.example.shake_and_forget.ui.theme.ElectricGrape
import com.example.shake_and_forget.ui.theme.MilkCream
import com.example.shake_and_forget.ui.theme.MintGreen
import com.example.shake_and_forget.ui.theme.PrimaryInk
import com.example.shake_and_forget.ui.theme.PureWhite
import com.example.shake_and_forget.ui.theme.SecondaryInk
import com.example.shake_and_forget.ui.theme.SunnyYellow
import com.example.shake_and_forget.ui.viewmodels.QuickDecisionViewModel

@Composable
fun QuickDecisionScreen(
    viewModel: QuickDecisionViewModel,
    onNavigateToShake: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MilkCream)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Quick Pick",
            style = MaterialTheme.typography.headlineLarge,
            color = PrimaryInk
        )

        // Category Selection Pills
        Text(
            text = "Step 1: Choose Category",
            style = MaterialTheme.typography.titleMedium,
            color = PrimaryInk
        )

        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(uiState.categories) { category ->
                val isSelected = uiState.selectedCategory?.id == category.id
                Box(
                    modifier = Modifier
                        .background(
                            if (isSelected) ElectricGrape else PureWhite,
                            shape = DesignTokens.PillShape
                        )
                        .border(DesignTokens.BorderStyle, shape = DesignTokens.PillShape)
                        .clickable { viewModel.selectCategory(category.id) }
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = category.name,
                        style = MaterialTheme.typography.labelLarge,
                        color = if (isSelected) PureWhite else PrimaryInk
                    )
                }
            }
        }

        // Available Decision Sets
        Text(
            text = "Step 2: Choose Decision List",
            style = MaterialTheme.typography.titleMedium,
            color = PrimaryInk
        )

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            uiState.availableSets.forEach { setDetails ->
                val isSelected = uiState.selectedSet?.decisionSet?.id == setDetails.decisionSet.id
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.selectDecisionSet(setDetails.decisionSet.id) }
                        .shadow(if (isSelected) 6.dp else 2.dp, DesignTokens.CardShape),
                    shape = DesignTokens.CardShape,
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) SunnyYellow else PureWhite
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
                        Column {
                            Text(
                                text = setDetails.decisionSet.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = PrimaryInk
                            )
                            Text(
                                text = "${setDetails.options.size} options available",
                                style = MaterialTheme.typography.bodyMedium,
                                color = SecondaryInk
                            )
                        }

                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Casino,
                                contentDescription = null,
                                tint = PrimaryInk,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }

        // Roll Action
        Button(
            onClick = { viewModel.rollRandomChoice() },
            enabled = uiState.selectedSet != null && !uiState.isRolling,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .shadow(elevation = 4.dp, shape = DesignTokens.PillShape),
            shape = DesignTokens.PillShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = CoralPink,
                contentColor = PureWhite
            )
        ) {
            Icon(
                imageVector = Icons.Default.Casino,
                contentDescription = null,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (uiState.isRolling) "ROLLING..." else "ROLL RANDOM CHOICE",
                style = MaterialTheme.typography.labelLarge
            )
        }

        // Result Card Reveal
        AnimatedVisibility(
            visible = uiState.rolledOption != null,
            enter = fadeIn(tween(300)) + scaleIn(tween(300))
        ) {
            uiState.rolledOption?.let { resultText ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(8.dp, DesignTokens.CardShape),
                    shape = DesignTokens.CardShape,
                    colors = CardDefaults.cardColors(containerColor = MintGreen),
                    border = DesignTokens.BorderStyle
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Celebration,
                            contentDescription = null,
                            tint = PrimaryInk,
                            modifier = Modifier.size(36.dp)
                        )
                        Text(
                            text = "DECISION MADE!",
                            style = MaterialTheme.typography.labelSmall,
                            color = PrimaryInk
                        )
                        Text(
                            text = resultText,
                            style = MaterialTheme.typography.displayLarge,
                            color = PrimaryInk
                        )
                    }
                }
            }
        }
    }
}
