package com.example.shake_and_forget.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shake_and_forget.data.local.entity.DecisionHistoryDetails
import com.example.shake_and_forget.data.repository.DecisionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class HistoryUiState(
    val historyList: List<DecisionHistoryDetails> = emptyList(),
    val isLoading: Boolean = true
)

class HistoryViewModel(
    private val repository: DecisionRepository
) : ViewModel() {

    val uiState: StateFlow<HistoryUiState> = repository.getAllHistoryDetails().map { history ->
        HistoryUiState(
            historyList = history,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HistoryUiState()
    )

    fun deleteHistoryEntry(id: Long) {
        viewModelScope.launch {
            repository.deleteHistoryById(id)
        }
    }

    fun clearAllHistory() {
        viewModelScope.launch {
            repository.clearAllHistory()
        }
    }
}
