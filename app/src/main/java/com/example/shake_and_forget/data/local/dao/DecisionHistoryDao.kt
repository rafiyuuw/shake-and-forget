package com.example.shake_and_forget.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.shake_and_forget.data.local.entity.DecisionHistoryDetails
import com.example.shake_and_forget.data.local.entity.DecisionHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DecisionHistoryDao {
    @Transaction
    @Query("SELECT * FROM decision_history ORDER BY createdAt DESC")
    fun getAllHistoryDetails(): Flow<List<DecisionHistoryDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: DecisionHistoryEntity): Long

    @Query("DELETE FROM decision_history WHERE id = :id")
    suspend fun deleteHistoryById(id: Long): Int

    @Query("DELETE FROM decision_history")
    suspend fun clearAllHistory(): Int
}
