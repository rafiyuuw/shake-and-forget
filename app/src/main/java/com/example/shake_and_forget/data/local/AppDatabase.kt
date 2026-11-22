package com.example.shake_and_forget.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.shake_and_forget.data.local.dao.CategoryDao
import com.example.shake_and_forget.data.local.dao.DecisionHistoryDao
import com.example.shake_and_forget.data.local.dao.DecisionSetDao
import com.example.shake_and_forget.data.local.dao.OptionDao
import com.example.shake_and_forget.data.local.entity.CategoryEntity
import com.example.shake_and_forget.data.local.entity.DecisionHistoryEntity
import com.example.shake_and_forget.data.local.entity.DecisionSetEntity
import com.example.shake_and_forget.data.local.entity.OptionEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        CategoryEntity::class,
        DecisionSetEntity::class,
        OptionEntity::class,
        DecisionHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun decisionSetDao(): DecisionSetDao
    abstract fun optionDao(): OptionDao
    abstract fun historyDao(): DecisionHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "shake_and_forget_db"
                )
                    .addCallback(DatabaseSeeder(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseSeeder(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    seedDatabase(database)
                }
            }
        }

        private suspend fun seedDatabase(database: AppDatabase) {
            val categoryDao = database.categoryDao()
            val decisionSetDao = database.decisionSetDao()
            val optionDao = database.optionDao()

            // 1. Seed Categories
            val catFoodId = categoryDao.insertCategory(
                CategoryEntity(name = "Food & Dining", colorHex = "#FF718D", iconName = "restaurant")
            )
            val catActivitiesId = categoryDao.insertCategory(
                CategoryEntity(name = "Activities & Fun", colorHex = "#69D6B2", iconName = "sports_esports")
            )
            val catQuickId = categoryDao.insertCategory(
                CategoryEntity(name = "Quick 50/50", colorHex = "#FFD166", iconName = "help")
            )
            val catMoviesId = categoryDao.insertCategory(
                CategoryEntity(name = "Movies & Shows", colorHex = "#70C5F5", iconName = "movie")
            )

            // 2. Seed Decision Sets & Options
            val setFoodId = decisionSetDao.insertDecisionSet(
                DecisionSetEntity(title = "What to eat for dinner?", categoryId = catFoodId)
            )
            optionDao.insertOptions(
                listOf(
                    OptionEntity(decisionSetId = setFoodId, title = "Pizza 🍕"),
                    OptionEntity(decisionSetId = setFoodId, title = "Ramen 🍜"),
                    OptionEntity(decisionSetId = setFoodId, title = "Sushi 🍣"),
                    OptionEntity(decisionSetId = setFoodId, title = "Tacos 🌮"),
                    OptionEntity(decisionSetId = setFoodId, title = "Burger 🍔")
                )
            )

            val setActivityId = decisionSetDao.insertDecisionSet(
                DecisionSetEntity(title = "Weekend Activity", categoryId = catActivitiesId)
            )
            optionDao.insertOptions(
                listOf(
                    OptionEntity(decisionSetId = setActivityId, title = "Go to the Movies 🎬"),
                    OptionEntity(decisionSetId = setActivityId, title = "Park Picnic 🧺"),
                    OptionEntity(decisionSetId = setActivityId, title = "Board Games at Home 🎲"),
                    OptionEntity(decisionSetId = setActivityId, title = "Coffee Shop Hop ☕")
                )
            )

            val setFiftyId = decisionSetDao.insertDecisionSet(
                DecisionSetEntity(title = "Should I do it?", categoryId = catQuickId)
            )
            optionDao.insertOptions(
                listOf(
                    OptionEntity(decisionSetId = setFiftyId, title = "Yes, absolutely! ✨"),
                    OptionEntity(decisionSetId = setFiftyId, title = "No, skip it! 🛑")
                )
            )
        }
    }
}
