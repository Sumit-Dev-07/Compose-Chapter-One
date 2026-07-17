package com.app.chapter_one.nav
import kotlinx.serialization.Serializable

@Serializable
data object Launcher

@Serializable
data object Login

@Serializable
data object SignUp

@Serializable
data class Otp(
    val mobileNumber: String
)
