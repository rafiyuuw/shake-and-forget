package com.example.shake_and_forget.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class DecisionSetDetails(
    @Embedded val decisionSet: DecisionSetEntity,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: CategoryEntity?,

    @Relation(
        parentColumn = "id",
        entityColumn = "decisionSetId"
    )
    val options: List<OptionEntity>
)
