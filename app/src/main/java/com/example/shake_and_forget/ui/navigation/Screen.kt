package com.example.shake_and_forget.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector? = null
) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object QuickDecision : Screen("quick_decision", "Quick Pick", Icons.Default.Casino)
    object MyLists : Screen("my_lists", "My Lists", Icons.AutoMirrored.Filled.FormatListBulleted)
    object History : Screen("history", "History", Icons.Default.History)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)

    // Shake to Decide is accessed via central FAB or quick trigger
    object ShakeToDecide : Screen("shake_to_decide", "Shake")

    companion object {
        val bottomNavItems = listOf(Home, QuickDecision, MyLists, History, Settings)
    }
}
