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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shake_and_forget.data.local.entity.DecisionSetDetails
import com.example.shake_and_forget.ui.theme.CoralPink
import com.example.shake_and_forget.ui.theme.DesignTokens
import com.example.shake_and_forget.ui.theme.ElectricGrape
import com.example.shake_and_forget.ui.theme.MilkCream
import com.example.shake_and_forget.ui.theme.MintGreen
import com.example.shake_and_forget.ui.theme.PrimaryInk
import com.example.shake_and_forget.ui.theme.PureWhite
import com.example.shake_and_forget.ui.theme.SecondaryInk
import com.example.shake_and_forget.ui.theme.SunnyYellow
import com.example.shake_and_forget.ui.viewmodels.DecisionListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyListsScreen(
    viewModel: DecisionListViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showEditorSheet by remember { mutableStateOf(false) }

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
                text = "My Lists",
                style = MaterialTheme.typography.headlineLarge,
                color = PrimaryInk
            )

            Button(
                onClick = {
                    viewModel.startEditingSet(null)
                    showEditorSheet = true
                },
                shape = DesignTokens.PillShape,
                colors = ButtonDefaults.buttonColors(containerColor = ElectricGrape, contentColor = PureWhite)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "NEW LIST", style = MaterialTheme.typography.labelLarge)
            }
        }

        if (uiState.decisionSets.isEmpty()) {
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
                        text = "No Lists Yet!",
                        style = MaterialTheme.typography.titleMedium,
                        color = PrimaryInk
                    )
                    Text(
                        text = "Create your first decision list to start shaking!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SecondaryInk
                    )
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(uiState.decisionSets, key = { it.decisionSet.id }) { setDetails ->
                    DecisionSetItemCard(
                        setDetails = setDetails,
                        onEdit = {
                            viewModel.startEditingSet(setDetails)
                            showEditorSheet = true
                        },
                        onDelete = { viewModel.deleteDecisionSet(setDetails.decisionSet.id) }
                    )
                }
            }
        }
    }

    if (showEditorSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showEditorSheet = false
                viewModel.clearValidationError()
            },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = MilkCream
        ) {
            DecisionSetEditorContent(
                editingSet = uiState.editingSet,
                categories = uiState.categories,
                validationError = uiState.validationError,
                onSave = { id, title, catId, options ->
                    viewModel.saveDecisionSet(id, title, catId, options)
                    if (title.isNotBlank() && options.filter { it.isNotBlank() }.size >= 2) {
                        showEditorSheet = false
                    }
                },
                onCancel = {
                    showEditorSheet = false
                    viewModel.clearValidationError()
                }
            )
        }
    }
}

@Composable
private fun DecisionSetItemCard(
    setDetails: DecisionSetDetails,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, DesignTokens.CardShape),
        shape = DesignTokens.CardShape,
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = DesignTokens.BorderStyle
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                        text = "${setDetails.options.size} choices available",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SecondaryInk
                    )
                }

                Row {
                    IconButton(onClick = onEdit) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", tint = ElectricGrape)
                    }
                    IconButton(onClick = onDelete) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = CoralPink)
                    }
                }
            }
        }
    }
}

@Composable
private fun DecisionSetEditorContent(
    editingSet: DecisionSetDetails?,
    categories: List<com.example.shake_and_forget.data.local.entity.CategoryEntity>,
    validationError: String?,
    onSave: (id: Long, title: String, categoryId: Long, options: List<String>) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf(editingSet?.decisionSet?.title ?: "") }
    var selectedCategoryId by remember { mutableStateOf(editingSet?.decisionSet?.categoryId ?: categories.firstOrNull()?.id ?: 1L) }
    val options = remember {
        mutableStateListOf<String>().apply {
            if (editingSet != null && editingSet.options.isNotEmpty()) {
                addAll(editingSet.options.map { it.title })
            } else {
                addAll(listOf("", ""))
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = if (editingSet == null) "Create New List" else "Edit List",
            style = MaterialTheme.typography.headlineMedium,
            color = PrimaryInk
        )

        // Validation Error Banner
        if (validationError != null) {
            Card(
                colors = CardDefaults.cardColors(containerColor = CoralPink),
                shape = DesignTokens.CardShape,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = PureWhite)
                    Text(text = validationError, style = MaterialTheme.typography.bodyMedium, color = PureWhite)
                }
            }
        }

        // Title Input
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("List Title") },
            placeholder = { Text("e.g. What movie to watch?") },
            modifier = Modifier.fillMaxWidth(),
            shape = DesignTokens.PillShape,
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = ElectricGrape)
        )

        // Category Selection
        Text(text = "Category", style = MaterialTheme.typography.labelLarge, color = PrimaryInk)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            categories.take(3).forEach { category ->
                val isSelected = selectedCategoryId == category.id
                Box(
                    modifier = Modifier
                        .background(if (isSelected) ElectricGrape else PureWhite, shape = DesignTokens.PillShape)
                        .border(DesignTokens.BorderStyle, shape = DesignTokens.PillShape)
                        .clickable { selectedCategoryId = category.id }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = category.name,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isSelected) PureWhite else PrimaryInk
                    )
                }
            }
        }

        // Options List Input
        Text(text = "Choices (Minimum 2 required)", style = MaterialTheme.typography.labelLarge, color = PrimaryInk)
        options.indices.forEach { index ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = options[index],
                    onValueChange = { options[index] = it },
                    placeholder = { Text("Option ${index + 1}") },
                    modifier = Modifier.weight(1f),
                    shape = DesignTokens.PillShape
                )
                if (options.size > 2) {
                    IconButton(onClick = { options.removeAt(index) }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Remove Option", tint = CoralPink)
                    }
                }
            }
        }

        Button(
            onClick = { options.add("") },
            shape = DesignTokens.PillShape,
            colors = ButtonDefaults.buttonColors(containerColor = SunnyYellow, contentColor = PrimaryInk)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Add Option")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onCancel,
                modifier = Modifier.weight(1f),
                shape = DesignTokens.PillShape,
                colors = ButtonDefaults.buttonColors(containerColor = PureWhite, contentColor = PrimaryInk)
            ) {
                Text("Cancel")
            }
            Button(
                onClick = {
                    onSave(
                        editingSet?.decisionSet?.id ?: 0L,
                        title,
                        selectedCategoryId,
                        options.toList()
                    )
                },
                modifier = Modifier.weight(1f),
                shape = DesignTokens.PillShape,
                colors = ButtonDefaults.buttonColors(containerColor = MintGreen, contentColor = PrimaryInk)
            ) {
                Text("Save List")
            }
        }
    }
}
