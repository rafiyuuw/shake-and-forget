package com.example.shake_and_forget.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "options",
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
data class OptionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val decisionSetId: Long,
    val title: String
)
