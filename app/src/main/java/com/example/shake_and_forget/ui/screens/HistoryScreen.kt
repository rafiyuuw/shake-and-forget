package com.example.shake_and_forget.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shake_and_forget.data.local.entity.DecisionHistoryDetails
import com.example.shake_and_forget.ui.theme.CoralPink
import com.example.shake_and_forget.ui.theme.DesignTokens
import com.example.shake_and_forget.ui.theme.ElectricGrape
import com.example.shake_and_forget.ui.theme.MilkCream
import com.example.shake_and_forget.ui.theme.PrimaryInk
import com.example.shake_and_forget.ui.theme.PureWhite
import com.example.shake_and_forget.ui.theme.SecondaryInk
import com.example.shake_and_forget.ui.viewmodels.HistoryViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MilkCream)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Decision History",
                style = MaterialTheme.typography.headlineLarge,
                color = PrimaryInk
            )

            if (uiState.historyList.isNotEmpty()) {
                Button(
                    onClick = { viewModel.clearAllHistory() },
                    shape = DesignTokens.PillShape,
                    colors = ButtonDefaults.buttonColors(containerColor = CoralPink, contentColor = PureWhite)
                ) {
                    Icon(imageVector = Icons.Default.DeleteSweep, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "CLEAR ALL", style = MaterialTheme.typography.labelSmall)
                }
            }
        }

        if (uiState.historyList.isEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                shape = DesignTokens.CardShape,
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = DesignTokens.BorderStyle
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "No History Yet!",
                        style = MaterialTheme.typography.titleMedium,
                        color = PrimaryInk
                    )
                    Text(
                        text = "Completed decision rolls will be recorded here.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SecondaryInk
                    )
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(uiState.historyList, key = { it.history.id }) { historyDetails ->
                    HistoryItemCard(
                        historyDetails = historyDetails,
                        onDelete = { viewModel.deleteHistoryEntry(historyDetails.history.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun HistoryItemCard(
    historyDetails: DecisionHistoryDetails,
    onDelete: () -> Unit
) {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val formattedDate = dateFormatter.format(Date(historyDetails.history.createdAt))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, DesignTokens.CardShape),
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
                    text = historyDetails.history.selectedOption,
                    style = MaterialTheme.typography.titleMedium,
                    color = ElectricGrape
                )
                Text(
                    text = historyDetails.decisionSet?.title ?: "Decision Set",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PrimaryInk
                )
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.labelSmall,
                    color = SecondaryInk
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete entry",
                    tint = CoralPink
                )
            }
        }
    }
}
