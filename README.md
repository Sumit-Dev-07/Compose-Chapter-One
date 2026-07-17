# Jetpack Compose Navigation Guide

This document provides a comprehensive explanation of how to implement and manage navigation in a Jetpack Compose application, based on the patterns used in this project.

## 1. Setup

To use Navigation in Compose, you need to add the following dependency to your `build.gradle.kts` (app level):

```kotlin
dependencies {
    implementation("androidx.navigation:navigation-compose:2.8.0") // Use the latest version
}
```

## 2. Defining Destinations

Destinations represent the different screens in your app. A common pattern is to use a `sealed class` to define your routes.

```kotlin
sealed class AppScreen(val route: String) {
    object Launcher : AppScreen("launcher")
    object Login : AppScreen("login")
    object SignUp : AppScreen("signup")
}
```

## 3. NavHost and NavController

The **`NavController`** is the central API for the Navigation component. It is stateful and keeps track of the back stack of composables that make up the screens in your app and the state of each screen.

The **`NavHost`** links the `NavController` with a navigation graph that specifies the composable destinations that you should be able to navigate between.

### Implementation Example (`AppNavHost.kt`)

```kotlin
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = AppScreen.Launcher.route
    ) {
        composable(AppScreen.Launcher.route) {
            LauncherScreen(onNavigateToLogin = {
                navController.navigate(AppScreen.Login.route) {
                    // Pop up to launcher and remove it from backstack
                    popUpTo(AppScreen.Launcher.route) { inclusive = true }
                }
            })
        }
        composable(AppScreen.Login.route) {
            LoginScreen(navigateToSignUp = {
                navController.navigate(AppScreen.SignUp.route)
            })
        }
        composable(AppScreen.SignUp.route) {
            SignUpScreen(navigateBack = {
                navController.popBackStack()
            })
        }
    }
}
```

## 4. Basic Navigation Operations

### Navigating to a Screen
```kotlin
navController.navigate("route_name")
```

### Navigating with Options
You can use `navOptions` to control the back stack:
```kotlin
navController.navigate(AppScreen.Login.route) {
    popUpTo(AppScreen.Launcher.route) { inclusive = true }
    launchSingleTop = true
}
```

### Going Back
```kotlin
navController.popBackStack()
// OR
navController.navigateUp()
```

## 5. Passing Arguments

To pass data between screens, you define placeholders in your route string.

### Define the route with arguments:
```kotlin
composable(
    route = "profile/{userId}",
    arguments = listOf(navArgument("userId") { type = NavType.StringType })
) { backStackEntry ->
    val userId = backStackEntry.arguments?.getString("userId")
    ProfileScreen(userId)
}
```

### Navigate with the value:
```kotlin
navController.navigate("profile/123")
```

## 6. Type-Safe Navigation (Modern Compose)

Starting with Navigation 2.8.0, you can use Kotlin Serialization for type-safe navigation.

### Define destinations as Serializable classes/objects:
```kotlin
@Serializable
object Launcher

@Serializable
data class Profile(val id: String)
```

### Setup NavHost:
```kotlin
NavHost(navController, startDestination = Launcher) {
    composable<Launcher> { 
        LauncherScreen(onNavigateToProfile = { id -> 
            navController.navigate(Profile(id)) 
        })
    }
    composable<Profile> { backStackEntry ->
        val profile: Profile = backStackEntry.toRoute()
        ProfileScreen(profile.id)
    }
}
```

## 7. Deep Linking

Deep links allow you to navigate to specific destinations from external sources (like a URL or a notification).

### Define a Deep Link in your route:
```kotlin
val uri = "https://www.example.com"

composable(
    route = "details/{id}",
    deepLinks = listOf(navDeepLink { uriPattern = "$uri/{id}" })
) { backStackEntry ->
    val id = backStackEntry.arguments?.getString("id")
    DetailsScreen(id)
}
```

### Configure AndroidManifest.xml:
```xml
<activity ...>
  <intent-filter>
    <action android:name="android.intent.action.VIEW" />
    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />
    <data android:scheme="https" android:host="www.example.com" />
  </intent-filter>
</activity>
```

## 8. Nested Navigation

Nested graphs allow you to group related destinations into a specific flow, such as a login flow or a checkout flow.

```kotlin
NavHost(navController, startDestination = "home") {
    composable("home") { ... }
    
    // Grouping auth-related screens
    navigation(startDestination = "login", route = "auth") {
        composable("login") { ... }
        composable("signup") { ... }
    }
}
```

To navigate to the entire nested graph:
```kotlin
navController.navigate("auth")
```

## 9. Best Practices

1.  **State Hoisting**: Pass lambdas (e.g., `onNavigateBack`, `onContinue`) to your screens instead of passing the `NavController` directly. This makes your screens more testable and reusable.
2.  **Single Source of Truth**: Define all your routes in one place (like `AppScreen.kt`).
3.  **Modularization**: For large apps, consider splitting your navigation graph into multiple nested graphs using `navigation` DSL.
