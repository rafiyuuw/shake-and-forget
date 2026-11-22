package com.example.shake_and_forget.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shake_and_forget.data.local.entity.OptionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OptionDao {
    @Query("SELECT * FROM options WHERE decisionSetId = :decisionSetId")
    fun getOptionsForDecisionSet(decisionSetId: Long): Flow<List<OptionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOption(option: OptionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOptions(options: List<OptionEntity>)

    @Update
    suspend fun updateOption(option: OptionEntity): Int

    @Delete
    suspend fun deleteOption(option: OptionEntity): Int

    @Query("DELETE FROM options WHERE decisionSetId = :decisionSetId")
    suspend fun deleteOptionsByDecisionSetId(decisionSetId: Long): Int
}
