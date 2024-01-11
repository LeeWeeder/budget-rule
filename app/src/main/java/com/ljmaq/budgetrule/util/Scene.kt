package com.ljmaq.budgetrule.util

sealed class Scene(val route: String) {
    data object BudgetRuleScene : Scene("main_scene")
    data object OnBoardingScene : Scene("onboarding_scene")
}