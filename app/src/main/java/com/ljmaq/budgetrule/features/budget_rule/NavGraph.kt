package com.ljmaq.budgetrule.features.budget_rule

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ljmaq.budgetrule.features.budget_rule.presentation.home.HomeScreen
import com.ljmaq.budgetrule.features.budget_rule.presentation.settings.SettingsScreen
import com.ljmaq.budgetrule.features.budget_rule.presentation.transaction.TransactionScreen
import com.ljmaq.budgetrule.features.budget_rule.util.Screen

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
}