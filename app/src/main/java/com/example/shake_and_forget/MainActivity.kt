package com.example.shake_and_forget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shake_and_forget.ui.navigation.AppNavGraph
import com.example.shake_and_forget.ui.navigation.CustomBottomNavigationBar
import com.example.shake_and_forget.ui.navigation.Screen
import com.example.shake_and_forget.ui.theme.MilkCream
import com.example.shake_and_forget.ui.theme.ShakeAndForgetTheme
import com.example.shake_and_forget.ui.viewmodels.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShakeAndForgetTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val viewModelFactory = remember { ViewModelFactory(applicationContext) }

                Scaffold(
                    containerColor = MilkCream,
                    bottomBar = {
                        CustomBottomNavigationBar(
                            currentRoute = currentRoute,
                            onNavigate = { screen ->
                                navController.navigate(screen.route) {
                                    popUpTo(Screen.Home.route) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    AppNavGraph(
                        navController = navController,
                        paddingValues = innerPadding,
                        viewModelFactory = viewModelFactory
                    )
                }
            }
        }
    }
}
