package com.example.shake_and_forget.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shake_and_forget.data.local.entity.CategoryEntity
import com.example.shake_and_forget.data.local.entity.DecisionSetDetails
import com.example.shake_and_forget.data.repository.DecisionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random

data class QuickDecisionUiState(
    val categories: List<CategoryEntity> = emptyList(),
    val selectedCategory: CategoryEntity? = null,
    val availableSets: List<DecisionSetDetails> = emptyList(),
    val selectedSet: DecisionSetDetails? = null,
    val rolledOption: String? = null,
    val isRolling: Boolean = false
)

class QuickDecisionViewModel(
    private val repository: DecisionRepository
) : ViewModel() {

    private val _selectedCategoryId = MutableStateFlow<Long?>(null)
    private val _selectedSetId = MutableStateFlow<Long?>(null)
    private val _rolledOption = MutableStateFlow<String?>(null)
    private val _isRolling = MutableStateFlow(false)

    val uiState: StateFlow<QuickDecisionUiState> = combine(
        combine(
            repository.getAllCategories(),
            repository.getAllDecisionSetsDetails(),
            _selectedCategoryId
        ) { categories, allSets, selectedCatId ->
            Triple(categories, allSets, selectedCatId)
        },
        combine(
            _selectedSetId,
            _rolledOption,
            _isRolling
        ) { selectedSetId, rolledOpt, isRolling ->
            Triple(selectedSetId, rolledOpt, isRolling)
        }
    ) { (categories, allSets, selectedCatId), (selectedSetId, rolledOpt, isRolling) ->
        val selectedCategory = categories.find { it.id == selectedCatId } ?: categories.firstOrNull()
        val availableSets = if (selectedCategory != null) {
            allSets.filter { it.decisionSet.categoryId == selectedCategory.id }
        } else {
            allSets
        }
        val selectedSet = availableSets.find { it.decisionSet.id == selectedSetId } ?: availableSets.firstOrNull()

        QuickDecisionUiState(
            categories = categories,
            selectedCategory = selectedCategory,
            availableSets = availableSets,
            selectedSet = selectedSet,
            rolledOption = rolledOpt,
            isRolling = isRolling
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = QuickDecisionUiState()
    )

    fun selectCategory(categoryId: Long) {
        _selectedCategoryId.value = categoryId
        _selectedSetId.value = null
        _rolledOption.value = null
    }

    fun selectDecisionSet(setId: Long) {
        _selectedSetId.value = setId
        _rolledOption.value = null
    }

    fun rollRandomChoice() {
        val currentSet = uiState.value.selectedSet ?: return
        val options = currentSet.options
        if (options.isEmpty()) return

        _isRolling.value = true
        viewModelScope.launch {
            kotlinx.coroutines.delay(400)
            val randomIndex = Random.nextInt(options.size)
            val result = options[randomIndex].title
            _rolledOption.value = result
            _isRolling.value = false

            repository.addHistoryEntry(currentSet.decisionSet.id, result)
        }
    }
}
