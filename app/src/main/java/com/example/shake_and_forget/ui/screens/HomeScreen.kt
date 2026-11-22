package com.example.shake_and_forget.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shake_and_forget.ui.theme.CoralPink
import com.example.shake_and_forget.ui.theme.DesignTokens
import com.example.shake_and_forget.ui.theme.ElectricGrape
import com.example.shake_and_forget.ui.theme.MilkCream
import com.example.shake_and_forget.ui.theme.MintGreen
import com.example.shake_and_forget.ui.theme.PrimaryInk
import com.example.shake_and_forget.ui.theme.PureWhite
import com.example.shake_and_forget.ui.theme.SecondaryInk
import com.example.shake_and_forget.ui.theme.SunnyYellow

@Composable
fun HomeScreen(
    onNavigateToShake: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MilkCream)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // App branding & Greeting
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

        // Hero Card (Tactile 3D style)
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
                        text = "Quick Decision",
                        style = MaterialTheme.typography.headlineMedium,
                        color = PrimaryInk
                    )
                }

                Text(
                    text = "Can't decide what to eat or do next? Pick a category and give your phone a gentle shake!",
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
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "SHAKE NOW",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }

        // Quick Category Badges
        Text(
            text = "Explore Categories",
            style = MaterialTheme.typography.titleMedium,
            color = PrimaryInk
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CategoryChip(name = "Food & Dining", color = CoralPink, modifier = Modifier.weight(1f))
            CategoryChip(name = "Activities", color = MintGreen, modifier = Modifier.weight(1f))
            CategoryChip(name = "50 / 50", color = SunnyYellow, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun CategoryChip(
    name: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(color, shape = DesignTokens.CardShape)
            .border(DesignTokens.BorderStyle, shape = DesignTokens.CardShape)
            .padding(vertical = 16.dp, horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelLarge,
            color = PrimaryInk
        )
    }
}
