package com.ljmaq.budgetrule.util

sealed class Screen(val route: String, val scene: Scene) {
    data object HomeScreen : Screen(route = "home_screen", scene = Scene.BudgetRuleScene)
    data object TransactionsScreen : Screen(route = "transactions_screen", scene = Scene.BudgetRuleScene)
    data object SettingsScreen : Screen(route = "settings_screen", scene = Scene.BudgetRuleScene)
    data object TuneSharePercentageScreen : Screen(route = "tune_share_percentage_screen", scene = Scene.BudgetRuleScene)
    data object OnBoardingScreen : Screen(route = "onboarding_screen", scene = Scene.OnBoardingScene)
}