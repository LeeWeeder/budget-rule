package com.ljmaq.budgetrule.features

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ljmaq.budgetrule.features.budget_rule.presentation.home.HomeScreen
import com.ljmaq.budgetrule.features.budget_rule.presentation.settings.SettingsScreen
import com.ljmaq.budgetrule.features.budget_rule.presentation.transaction.TransactionScreen
import com.ljmaq.budgetrule.features.onboarding.presentation.OnBoardingScreen
import com.ljmaq.budgetrule.features.util.Scene
import com.ljmaq.budgetrule.features.util.Screen

@Composable
fun SetupNavHost(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        /* composable(
            route = Screen.HomeScreen.route + "?recordId={recordId}",
            arguments = listOf(
                navArgument(name = "recordId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            ),
            enterTransition = enterTransition,
            exitTransition = exitTransition
        ) {
            HomeScreen(navController)
        }*/
        navigation(
            startDestination = Screen.HomeScreen.route,
            route = Scene.BudgetRuleAppScene.route
        ) {
            composable(
                route = Screen.HomeScreen.route
            ) {
                HomeScreen(navController)
            }

            composable(
                route = Screen.TransactionsScreen.route
            ) {
                TransactionScreen()
            }

            composable(
                route = Screen.SettingsScreen.route
            ) {
                SettingsScreen(navController = navController)
            }

            composable(
                route = Screen.TuneSharePercentageScreen.route
            ) {
                SettingsScreen(navController = navController)
            }
        }

        navigation(
            startDestination = Screen.OnBoardingScreen.route,
            route = Scene.OnBoardingScene.route
        ) {
            composable(Screen.OnBoardingScreen.route) {
                OnBoardingScreen(navController = navController)
            }
        }
    }
}