package com.example.shake_and_forget.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shake_and_forget.ui.screens.HistoryScreen
import com.example.shake_and_forget.ui.screens.HomeScreen
import com.example.shake_and_forget.ui.screens.MyListsScreen
import com.example.shake_and_forget.ui.screens.QuickDecisionScreen
import com.example.shake_and_forget.ui.screens.SettingsScreen
import com.example.shake_and_forget.ui.screens.ShakeToDecideScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Screen.Home.route) {
            HomeScreen(onNavigateToShake = { navController.navigate(Screen.ShakeToDecide.route) })
        }
        composable(Screen.QuickDecision.route) {
            QuickDecisionScreen()
        }
        composable(Screen.MyLists.route) {
            MyListsScreen()
        }
        composable(Screen.History.route) {
            HistoryScreen()
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
        composable(Screen.ShakeToDecide.route) {
            ShakeToDecideScreen()
        }
    }
}
