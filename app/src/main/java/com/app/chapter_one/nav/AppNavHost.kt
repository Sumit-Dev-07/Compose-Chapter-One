package com.app.chapter_one.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.app.chapter_one.ui.features.auth.screen.LoginScreen
import com.app.chapter_one.ui.features.auth.screen.OtpScreen
import com.app.chapter_one.ui.features.auth.screen.SignUpScreen
import com.app.chapter_one.ui.features.common.screen.LauncherScreen

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Launcher
    ) {

        composable<Launcher> {

            LauncherScreen(
                onNavigateToMain = {
                    navController.navigate(Login) {
                        popUpTo<Launcher> {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Login>(
            exitTransition = NavAnimations.exit(),
            popEnterTransition = NavAnimations.popEnter(),
            popExitTransition = NavAnimations.popExit()
        ) {

            LoginScreen(

                navigateToSignUp = {
                    navController.navigate(SignUp)
                },

                navigateToOtpScreen = { mobile ->
                    navController.navigate(Otp(mobile))
                }
            )
        }

        composable<SignUp>(
            enterTransition = NavAnimations.enter(),
            exitTransition = NavAnimations.fadeOutOnly(),
            popExitTransition = NavAnimations.popExit()
        ) {

            SignUpScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<Otp> { backStackEntry ->

            val otp: Otp = backStackEntry.toRoute()

            OtpScreen(
                navigateBack = { navController.navigateUp() },
                mobileNumber = otp.mobileNumber
            )
        }
    }
}
