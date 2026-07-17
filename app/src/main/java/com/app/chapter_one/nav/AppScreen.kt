package com.app.chapter_one.nav

sealed class AppScreen(val route: String) {
    object Launcher : AppScreen("launcher")
    object Login : AppScreen("login")
    object SignUp : AppScreen("signup")
    object Otp : AppScreen("otp/{${NavArgs.ARG_MOBILE_NUMBER}") {
        fun createRoute(mobileNumber: String) = "otp/$mobileNumber"
    }
}
