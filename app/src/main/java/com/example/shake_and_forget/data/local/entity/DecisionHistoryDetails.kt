package com.example.shake_and_forget.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class DecisionHistoryDetails(
    @Embedded val history: DecisionHistoryEntity,

    @Relation(
        parentColumn = "decisionSetId",
        entityColumn = "id"
    )
    val decisionSet: DecisionSetEntity?
)
