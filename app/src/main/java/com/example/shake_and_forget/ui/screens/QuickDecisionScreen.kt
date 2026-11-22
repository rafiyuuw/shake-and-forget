package com.example.shake_and_forget.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.shake_and_forget.ui.theme.DesignTokens
import com.example.shake_and_forget.ui.theme.MilkCream
import com.example.shake_and_forget.ui.theme.PrimaryInk
import com.example.shake_and_forget.ui.theme.PureWhite
import com.example.shake_and_forget.ui.theme.SecondaryInk

@Composable
fun QuickDecisionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MilkCream)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Quick Pick",
            style = MaterialTheme.typography.headlineLarge,
            color = PrimaryInk
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, DesignTokens.CardShape),
            shape = DesignTokens.CardShape,
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            border = DesignTokens.BorderStyle
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Select a Category",
                    style = MaterialTheme.typography.titleMedium,
                    color = PrimaryInk
                )
                Text(
                    text = "Pick one of your decision categories to roll a quick choice.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SecondaryInk
                )
            }
        }
    }
}
