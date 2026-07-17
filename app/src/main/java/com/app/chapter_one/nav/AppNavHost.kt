package com.app.chapter_one.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.chapter_one.ui.features.auth.screen.LoginScreen
import com.app.chapter_one.ui.features.common.screen.LauncherScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Launcher.route
    ) {
        composable(AppScreen.Launcher.route) {
            LauncherScreen(onNavigateToMain = {
                navController.navigate(AppScreen.Login.route) {
                    popUpTo(AppScreen.Launcher.route) { inclusive = true }
                }
            })
        }
        composable(AppScreen.Login.route) {
            LoginScreen()
        }
    }
}
