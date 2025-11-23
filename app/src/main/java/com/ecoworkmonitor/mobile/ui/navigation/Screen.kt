package com.ecoworkmonitor.mobile.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object Settings : Screen("settings")
    object History : Screen("history")
}
