package com.ljmaq.budgetrule

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ljmaq.budgetrule.presentation.home.HomeScreen
import com.ljmaq.budgetrule.presentation.onboarding.OnBoardingScreen
import com.ljmaq.budgetrule.presentation.onboarding.OtherScreen
import com.ljmaq.budgetrule.presentation.settings.SettingsScreen
import com.ljmaq.budgetrule.presentation.tune_share_percentage.TuneSharePercentageScreen
import com.ljmaq.budgetrule.util.Scene
import com.ljmaq.budgetrule.util.Screen

@Composable
fun SetupNavHost(
    navController: NavHostController,
    startDestination: String,
    viewModel: MainActivityViewModel
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(startDestination = Screen.HomeScreen.route, route = Scene.BudgetRuleScene.route) {
            composable(
                route = Screen.HomeScreen.route + "?fromOnBoarding={fromOnBoarding}",
                arguments = listOf(navArgument("fromOnBoarding") {defaultValue = false})
            ) { backStackEntry ->
                backStackEntry.arguments?.getBoolean("fromOnBoarding")?.let {
                    HomeScreen(navController = navController, onNavigateToOnBoardingScreen = {
                        navController.navigate(Scene.OnBoardingScene.route)
                        viewModel.setIsLoading(false)
                    }, onNotShowOnBoarding = {
                        viewModel.setIsLoading(false)
                    }, fromOnBoarding = it)
                }
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
        navigation(startDestination = Screen.FirstScreen.route, route = Scene.OnBoardingScene.route) {
            composable(
                route = Screen.FirstScreen.route
            ) {
                OnBoardingScreen(navController)
            }
            composable(
                route = Screen.OtherScreen.route
            ) {
                OtherScreen()
            }
        }
    }
}