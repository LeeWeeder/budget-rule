package com.ljmaq.budgetrule.features.util

sealed class Scene(val route: String) {
    data object OnBoardingScene: Scene("onboarding_scene")
    data object BudgetRuleAppScene: Scene("budget_rule_app_scene")
}