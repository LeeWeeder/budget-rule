package com.ljmaq.budgetrule.util

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_screen")
    data object TransactionsScreen : Screen("transactions_screen")
    data object SettingsScreen : Screen("settings_screen")
    data object TuneSharePercentageScreen : Screen("tune_share_percentage_screen")
}