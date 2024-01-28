package com.ljmaq.budgetrule.util

sealed class Screen(val route: String) {
    data object HomeScreen : Screen(route = "home_screen")
    data object TransactionsScreen : Screen(route = "transactions_screen")
    data object SettingsScreen : Screen(route = "settings_screen")
    data object TuneSharePercentageScreen : Screen(route = "tune_share_percentage_screen")
    data object FirstScreen : Screen(route = "first_screen")
    data object OtherScreen : Screen(route = "other_screen")
}