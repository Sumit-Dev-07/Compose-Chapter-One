package com.app.chapter_one.nav

sealed class AppScreen(val route: String) {
    object Launcher : AppScreen("launcher")
    object Login : AppScreen("login")
    object SignUp : AppScreen("signup")
}
