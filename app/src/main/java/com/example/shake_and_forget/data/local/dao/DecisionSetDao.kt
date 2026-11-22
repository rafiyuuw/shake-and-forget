package com.example.shake_and_forget.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.shake_and_forget.data.local.entity.DecisionSetDetails
import com.example.shake_and_forget.data.local.entity.DecisionSetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DecisionSetDao {
    @Transaction
    @Query("SELECT * FROM decision_sets ORDER BY updatedAt DESC")
    fun getAllDecisionSetsDetails(): Flow<List<DecisionSetDetails>>

    @Transaction
    @Query("SELECT * FROM decision_sets WHERE categoryId = :categoryId ORDER BY updatedAt DESC")
    fun getDecisionSetsByCategory(categoryId: Long): Flow<List<DecisionSetDetails>>

    @Transaction
    @Query("SELECT * FROM decision_sets WHERE id = :id")
    suspend fun getDecisionSetDetailsById(id: Long): DecisionSetDetails?

    @Transaction
    @Query("SELECT * FROM decision_sets WHERE id = :id")
    fun getDecisionSetDetailsByIdFlow(id: Long): Flow<DecisionSetDetails?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDecisionSet(decisionSet: DecisionSetEntity): Long

    @Update
    suspend fun updateDecisionSet(decisionSet: DecisionSetEntity): Int

    @Delete
    suspend fun deleteDecisionSet(decisionSet: DecisionSetEntity): Int

    @Query("DELETE FROM decision_sets WHERE id = :id")
    suspend fun deleteDecisionSetById(id: Long): Int
}
