package com.example.shake_and_forget.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "decision_history",
    foreignKeys = [
        ForeignKey(
            entity = DecisionSetEntity::class,
            parentColumns = ["id"],
            childColumns = ["decisionSetId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["decisionSetId"])]
)
data class DecisionHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val decisionSetId: Long,
    val selectedOption: String,
    val createdAt: Long = System.currentTimeMillis()
)
