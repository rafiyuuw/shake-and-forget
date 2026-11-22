package com.example.shake_and_forget.data.repository

import com.example.shake_and_forget.data.local.entity.CategoryEntity
import com.example.shake_and_forget.data.local.entity.DecisionHistoryDetails
import com.example.shake_and_forget.data.local.entity.DecisionSetDetails
import com.example.shake_and_forget.data.local.entity.DecisionSetEntity
import kotlinx.coroutines.flow.Flow

interface DecisionRepository {
    // Categories
    fun getAllCategories(): Flow<List<CategoryEntity>>
    suspend fun getCategoryById(id: Long): CategoryEntity?
    suspend fun insertCategory(category: CategoryEntity): Long
    suspend fun updateCategory(category: CategoryEntity): Int
    suspend fun deleteCategory(category: CategoryEntity): Int

    // Decision Sets & Options
    fun getAllDecisionSetsDetails(): Flow<List<DecisionSetDetails>>
    fun getDecisionSetsByCategory(categoryId: Long): Flow<List<DecisionSetDetails>>
    suspend fun getDecisionSetDetailsById(id: Long): DecisionSetDetails?
    fun getDecisionSetDetailsByIdFlow(id: Long): Flow<DecisionSetDetails?>
    suspend fun saveDecisionSetWithOptions(
        decisionSet: DecisionSetEntity,
        options: List<String>
    ): Long
    suspend fun updateDecisionSetWithOptions(
        decisionSet: DecisionSetEntity,
        options: List<String>
    )
    suspend fun deleteDecisionSetById(id: Long): Int

    // Decision History
    fun getAllHistoryDetails(): Flow<List<DecisionHistoryDetails>>
    suspend fun addHistoryEntry(decisionSetId: Long, selectedOption: String): Long
    suspend fun deleteHistoryById(id: Long): Int
    suspend fun clearAllHistory(): Int
}
