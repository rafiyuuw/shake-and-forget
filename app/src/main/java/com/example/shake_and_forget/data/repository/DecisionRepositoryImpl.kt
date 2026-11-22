package com.example.shake_and_forget.data.repository

import com.example.shake_and_forget.data.local.dao.CategoryDao
import com.example.shake_and_forget.data.local.dao.DecisionHistoryDao
import com.example.shake_and_forget.data.local.dao.DecisionSetDao
import com.example.shake_and_forget.data.local.dao.OptionDao
import com.example.shake_and_forget.data.local.entity.CategoryEntity
import com.example.shake_and_forget.data.local.entity.DecisionHistoryDetails
import com.example.shake_and_forget.data.local.entity.DecisionHistoryEntity
import com.example.shake_and_forget.data.local.entity.DecisionSetDetails
import com.example.shake_and_forget.data.local.entity.DecisionSetEntity
import com.example.shake_and_forget.data.local.entity.OptionEntity
import kotlinx.coroutines.flow.Flow

class DecisionRepositoryImpl(
    private val categoryDao: CategoryDao,
    private val decisionSetDao: DecisionSetDao,
    private val optionDao: OptionDao,
    private val historyDao: DecisionHistoryDao
) : DecisionRepository {

    override fun getAllCategories(): Flow<List<CategoryEntity>> =
        categoryDao.getAllCategories()

    override suspend fun getCategoryById(id: Long): CategoryEntity? =
        categoryDao.getCategoryById(id)

    override suspend fun insertCategory(category: CategoryEntity): Long =
        categoryDao.insertCategory(category)

    override suspend fun updateCategory(category: CategoryEntity): Int =
        categoryDao.updateCategory(category)

    override suspend fun deleteCategory(category: CategoryEntity): Int =
        categoryDao.deleteCategory(category)

    override fun getAllDecisionSetsDetails(): Flow<List<DecisionSetDetails>> =
        decisionSetDao.getAllDecisionSetsDetails()

    override fun getDecisionSetsByCategory(categoryId: Long): Flow<List<DecisionSetDetails>> =
        decisionSetDao.getDecisionSetsByCategory(categoryId)

    override suspend fun getDecisionSetDetailsById(id: Long): DecisionSetDetails? =
        decisionSetDao.getDecisionSetDetailsById(id)

    override fun getDecisionSetDetailsByIdFlow(id: Long): Flow<DecisionSetDetails?> =
        decisionSetDao.getDecisionSetDetailsByIdFlow(id)

    override suspend fun saveDecisionSetWithOptions(
        decisionSet: DecisionSetEntity,
        options: List<String>
    ): Long {
        val setId = decisionSetDao.insertDecisionSet(decisionSet)
        val optionEntities = options.map { OptionEntity(decisionSetId = setId, title = it) }
        optionDao.insertOptions(optionEntities)
        return setId
    }

    override suspend fun updateDecisionSetWithOptions(
        decisionSet: DecisionSetEntity,
        options: List<String>
    ) {
        decisionSetDao.updateDecisionSet(decisionSet.copy(updatedAt = System.currentTimeMillis()))
        optionDao.deleteOptionsByDecisionSetId(decisionSet.id)
        val optionEntities = options.map { OptionEntity(decisionSetId = decisionSet.id, title = it) }
        optionDao.insertOptions(optionEntities)
    }

    override suspend fun deleteDecisionSetById(id: Long): Int =
        decisionSetDao.deleteDecisionSetById(id)

    override fun getAllHistoryDetails(): Flow<List<DecisionHistoryDetails>> =
        historyDao.getAllHistoryDetails()

    override suspend fun addHistoryEntry(decisionSetId: Long, selectedOption: String): Long {
        val history = DecisionHistoryEntity(
            decisionSetId = decisionSetId,
            selectedOption = selectedOption
        )
        return historyDao.insertHistory(history)
    }

    override suspend fun deleteHistoryById(id: Long): Int =
        historyDao.deleteHistoryById(id)

    override suspend fun clearAllHistory(): Int =
        historyDao.clearAllHistory()
}
