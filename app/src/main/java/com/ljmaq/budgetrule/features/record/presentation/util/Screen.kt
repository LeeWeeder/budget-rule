package com.ljmaq.budgetrule.features.record.presentation.util

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_screen")
    data object SettingsScreen : Screen("settings_screen")
}
