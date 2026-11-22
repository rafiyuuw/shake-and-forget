package com.example.shake_and_forget.ui.screens

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
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Vibration
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shake_and_forget.data.local.entity.CategoryEntity
import com.example.shake_and_forget.data.local.entity.DecisionSetDetails
import com.example.shake_and_forget.ui.theme.CoralPink
import com.example.shake_and_forget.ui.theme.DesignTokens
import com.example.shake_and_forget.ui.theme.ElectricGrape
import com.example.shake_and_forget.ui.theme.MilkCream
import com.example.shake_and_forget.ui.theme.MintGreen
import com.example.shake_and_forget.ui.theme.PrimaryInk
import com.example.shake_and_forget.ui.theme.PureWhite
import com.example.shake_and_forget.ui.theme.SecondaryInk
import com.example.shake_and_forget.ui.theme.SkyBlue
import com.example.shake_and_forget.ui.theme.SunnyYellow
import com.example.shake_and_forget.ui.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToShake: () -> Unit = {},
    onNavigateToQuickPick: () -> Unit = {}
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
        // App Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "SHAKE & FORGET",
                    style = MaterialTheme.typography.labelSmall,
                    color = ElectricGrape,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "Stop overthinking.",
                    style = MaterialTheme.typography.headlineLarge,
                    color = PrimaryInk
                )
            }
            Box(
                modifier = Modifier
                    .background(SunnyYellow, shape = DesignTokens.PillShape)
                    .border(DesignTokens.BorderStyle, shape = DesignTokens.PillShape)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Ready to Shake!",
                    style = MaterialTheme.typography.labelSmall,
                    color = PrimaryInk
                )
            }
        }

        // Kinetic Shake CTA Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 4.dp, shape = DesignTokens.CardShape),
            shape = DesignTokens.CardShape,
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            border = DesignTokens.BorderStyle
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = null,
                        tint = ElectricGrape,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Instant Shake",
                        style = MaterialTheme.typography.headlineMedium,
                        color = PrimaryInk
                    )
                }

                Text(
                    text = "Can't decide what to eat, watch, or do? Shake your phone to roll an instant decision!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = SecondaryInk
                )

                Button(
                    onClick = onNavigateToShake,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .shadow(elevation = 4.dp, shape = DesignTokens.PillShape),
                    shape = DesignTokens.PillShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ElectricGrape,
                        contentColor = PureWhite
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Vibration,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "SHAKE NOW",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }

        // Decision Categories
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Decision Categories",
                style = MaterialTheme.typography.titleMedium,
                color = PrimaryInk
            )
            Text(
                text = "Quick Pick",
                style = MaterialTheme.typography.labelLarge,
                color = ElectricGrape,
                modifier = Modifier.clickable { onNavigateToQuickPick() }
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(uiState.categories) { category ->
                HomeCategoryBadge(
                    category = category,
                    onClick = { onNavigateToQuickPick() }
                )
            }
        }

        // Recent Decision Lists
        Text(
            text = "Recently Used Lists",
            style = MaterialTheme.typography.titleMedium,
            color = PrimaryInk
        )

        uiState.recentDecisionSets.forEach { setDetails ->
            RecentSetCard(
                setDetails = setDetails,
                onClick = { onNavigateToShake() }
            )
        }
    }
}

@Composable
private fun HomeCategoryBadge(
    category: CategoryEntity,
    onClick: () -> Unit
) {
    val badgeColor = when (category.id % 4) {
        0L -> CoralPink
        1L -> MintGreen
        2L -> SunnyYellow
        else -> SkyBlue
    }

    Box(
        modifier = Modifier
            .background(badgeColor, shape = DesignTokens.CardShape)
            .border(DesignTokens.BorderStyle, shape = DesignTokens.CardShape)
            .clickable { onClick() }
            .padding(horizontal = 18.dp, vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = category.name,
            style = MaterialTheme.typography.labelLarge,
            color = PrimaryInk
        )
    }
}

@Composable
private fun RecentSetCard(
    setDetails: DecisionSetDetails,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .shadow(elevation = 2.dp, shape = DesignTokens.CardShape),
        shape = DesignTokens.CardShape,
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = DesignTokens.BorderStyle
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
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

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = ElectricGrape,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
