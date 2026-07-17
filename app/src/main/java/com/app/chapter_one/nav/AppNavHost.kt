package com.app.chapter_one.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.chapter_one.ui.features.auth.screen.LoginScreen
import com.app.chapter_one.ui.features.auth.screen.OtpScreen
import com.app.chapter_one.ui.features.auth.screen.SignUpScreen
import com.app.chapter_one.ui.features.common.screen.LauncherScreen

@Composable
fun AppNavHost() {
    val navController: NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreen.Launcher.route
    ) {
        // Launcher Screen
        composable(
            AppScreen.Launcher.route,
        ) {
            LauncherScreen(onNavigateToMain = {
                navController.navigate(AppScreen.Login.route) {
                    popUpTo(AppScreen.Launcher.route) { inclusive = true }
                }
            })
        }

        // Login Screen
        composable(
            AppScreen.Login.route,
            exitTransition = NavAnimations.exit(),
            popEnterTransition = NavAnimations.popEnter(),
            popExitTransition = NavAnimations.popExit(),
        ) {
            LoginScreen(navigateToSignUp = {
                navController.navigate(AppScreen.SignUp.route)
            }, navigateToOtpScreen = { mobile ->
                navController.navigate(AppScreen.Otp.createRoute(mobile))
            })
        }

        // SignUp Screen
        composable(
            AppScreen.SignUp.route,
            enterTransition = NavAnimations.enter(),
            exitTransition = NavAnimations.fadeOutOnly(),
            popExitTransition = NavAnimations.popExit()
        ) {
            SignUpScreen(navigateBack = {
                navController.navigateUp()
            })
        }

        // Otp Screen
        composable(
            AppScreen.Otp.route,
            arguments = listOf(navArgument(NavArgs.ARG_MOBILE_NUMBER) { type = NavType.StringType })
        ) { backStackEntry ->
            val mobileNumber = backStackEntry.arguments?.getString(NavArgs.ARG_MOBILE_NUMBER) ?: ""
            OtpScreen(navigateBack = { navController.navigateUp() }, mobileNumber = mobileNumber)
        }
    }
}
