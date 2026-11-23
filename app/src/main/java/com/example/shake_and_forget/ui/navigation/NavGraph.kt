package com.example.shake_and_forget.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shake_and_forget.ui.screens.HistoryScreen
import com.example.shake_and_forget.ui.screens.HomeScreen
import com.example.shake_and_forget.ui.screens.MyListsScreen
import com.example.shake_and_forget.ui.screens.QuickDecisionScreen
import com.example.shake_and_forget.ui.screens.SettingsScreen
import com.example.shake_and_forget.ui.screens.ShakeToDecideScreen
import com.example.shake_and_forget.ui.viewmodels.DecisionListViewModel
import com.example.shake_and_forget.ui.viewmodels.HistoryViewModel
import com.example.shake_and_forget.ui.viewmodels.HomeViewModel
import com.example.shake_and_forget.ui.viewmodels.QuickDecisionViewModel
import com.example.shake_and_forget.ui.viewmodels.SettingsViewModel
import com.example.shake_and_forget.ui.viewmodels.ShakeDecisionViewModel
import com.example.shake_and_forget.ui.viewmodels.ViewModelFactory

@Composable
fun AppNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Screen.Home.route) {
            val homeViewModel: HomeViewModel = viewModel(factory = viewModelFactory)
            HomeScreen(
                viewModel = homeViewModel,
                onNavigateToShake = { navController.navigate(Screen.ShakeToDecide.route) },
                onNavigateToQuickPick = { navController.navigate(Screen.QuickDecision.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }
        composable(Screen.QuickDecision.route) {
            val quickViewModel: QuickDecisionViewModel = viewModel(factory = viewModelFactory)
            QuickDecisionScreen(
                viewModel = quickViewModel,
                onNavigateToShake = { navController.navigate(Screen.ShakeToDecide.route) }
            )
        }
        composable(Screen.MyLists.route) {
            val listViewModel: DecisionListViewModel = viewModel(factory = viewModelFactory)
            MyListsScreen(viewModel = listViewModel)
        }
        composable(Screen.History.route) {
            val historyViewModel: HistoryViewModel = viewModel(factory = viewModelFactory)
            HistoryScreen(viewModel = historyViewModel)
        }
        composable(Screen.Settings.route) {
            val settingsViewModel: SettingsViewModel = viewModel(factory = viewModelFactory)
            SettingsScreen(
                viewModel = settingsViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.ShakeToDecide.route) {
            val shakeViewModel: ShakeDecisionViewModel = viewModel(factory = viewModelFactory)
            ShakeToDecideScreen(viewModel = shakeViewModel)
        }
    }
}
