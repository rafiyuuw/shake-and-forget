package com.example.shake_and_forget.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shake_and_forget.data.local.entity.CategoryEntity
import com.example.shake_and_forget.data.local.entity.DecisionSetDetails
import com.example.shake_and_forget.data.repository.DecisionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class HomeUiState(
    val categories: List<CategoryEntity> = emptyList(),
    val recentDecisionSets: List<DecisionSetDetails> = emptyList(),
    val isLoading: Boolean = true
)

class HomeViewModel(
    private val repository: DecisionRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = combine(
        repository.getAllCategories(),
        repository.getAllDecisionSetsDetails()
    ) { categories, decisionSets ->
        HomeUiState(
            categories = categories,
            recentDecisionSets = decisionSets.take(5),
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState()
    )
}
