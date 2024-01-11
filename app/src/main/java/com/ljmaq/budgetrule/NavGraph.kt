package com.ljmaq.budgetrule

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ljmaq.budgetrule.presentation.home.HomeScreen
import com.ljmaq.budgetrule.presentation.onboarding.OnBoardingScreen
import com.ljmaq.budgetrule.presentation.settings.SettingsScreen
import com.ljmaq.budgetrule.presentation.tune_share_percentage.TuneSharePercentageScreen
import com.ljmaq.budgetrule.util.Scene
import com.ljmaq.budgetrule.util.Screen

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
        navigation(startDestination = Screen.HomeScreen.route, route = Scene.BudgetRuleScene.route) {
            composable(
                route = Screen.HomeScreen.route
            ) {
                HomeScreen(navController)
            }

            composable(
                route = Screen.TransactionsScreen.route
            ) {
                SettingsScreen(navController = navController)
            }

            composable(
                route = Screen.SettingsScreen.route
            ) {
                SettingsScreen(navController = navController)
            }

            composable(
                route = Screen.TuneSharePercentageScreen.route
            ) {
                TuneSharePercentageScreen(navController = navController)
            }
        }
        navigation(startDestination = Screen.OnBoardingScreen.route, route = Scene.OnBoardingScene.route) {
            composable(
                route = Screen.OnBoardingScreen.route
            ) {
                OnBoardingScreen()
            }
        }
    }
}