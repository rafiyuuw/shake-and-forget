package com.example.shake_and_forget.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shake_and_forget.data.local.entity.CategoryEntity
import com.example.shake_and_forget.data.local.entity.DecisionSetDetails
import com.example.shake_and_forget.data.local.entity.DecisionSetEntity
import com.example.shake_and_forget.data.repository.DecisionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class DecisionListUiState(
    val categories: List<CategoryEntity> = emptyList(),
    val decisionSets: List<DecisionSetDetails> = emptyList(),
    val editingSet: DecisionSetDetails? = null,
    val validationError: String? = null
)

class DecisionListViewModel(
    private val repository: DecisionRepository
) : ViewModel() {

    private val _editingSet = MutableStateFlow<DecisionSetDetails?>(null)
    private val _validationError = MutableStateFlow<String?>(null)

    val uiState: StateFlow<DecisionListUiState> = combine(
        repository.getAllCategories(),
        repository.getAllDecisionSetsDetails(),
        _editingSet,
        _validationError
    ) { categories, sets, editing, error ->
        DecisionListUiState(
            categories = categories,
            decisionSets = sets,
            editingSet = editing,
            validationError = error
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DecisionListUiState()
    )

    fun startEditingSet(set: DecisionSetDetails?) {
        _editingSet.value = set
        _validationError.value = null
    }

    fun saveDecisionSet(
        id: Long = 0,
        title: String,
        categoryId: Long,
        options: List<String>
    ) {
        val trimmedTitle = title.trim()
        val validOptions = options.map { it.trim() }.filter { it.isNotEmpty() }

        if (trimmedTitle.isEmpty()) {
            _validationError.value = "List title cannot be empty."
            return
        }
        if (validOptions.size < 2) {
            _validationError.value = "Please add at least 2 options to make a decision."
            return
        }

        viewModelScope.launch {
            if (id == 0L) {
                repository.saveDecisionSetWithOptions(
                    decisionSet = DecisionSetEntity(title = trimmedTitle, categoryId = categoryId),
                    options = validOptions
                )
            } else {
                repository.updateDecisionSetWithOptions(
                    decisionSet = DecisionSetEntity(id = id, title = trimmedTitle, categoryId = categoryId),
                    options = validOptions
                )
            }
            _editingSet.value = null
            _validationError.value = null
        }
    }

    fun deleteDecisionSet(id: Long) {
        viewModelScope.launch {
            repository.deleteDecisionSetById(id)
        }
    }

    fun clearValidationError() {
        _validationError.value = null
    }
}
