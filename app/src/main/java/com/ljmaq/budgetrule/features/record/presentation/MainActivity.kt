package com.ljmaq.budgetrule.features.record.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ljmaq.budgetrule.features.record.presentation.edit_record.EditRecordScreen
import com.ljmaq.budgetrule.features.record.presentation.records.RecordScreen
import com.ljmaq.budgetrule.features.record.presentation.util.Screen
import com.ljmaq.budgetrule.ui.theme.BudgetRuleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BudgetRuleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.RecordScreen.route
                    ) {
                        val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? =
                            {
                                fadeIn(spring())
                            }


                        val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? =
                            {
                                fadeOut(spring())
                            }

                        composable(
                            route = Screen.RecordScreen.route,
                            enterTransition = enterTransition,
                            exitTransition = exitTransition
                        ) {
                            RecordScreen(navController)
                        }

                        composable(route = Screen.EditRecordScreen.route + "?recordId={recordId}",
                            arguments = listOf(
                                navArgument(name = "recordId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            ),
                            enterTransition = enterTransition,
                            exitTransition = exitTransition) {
                            EditRecordScreen(navController)
                        }
                    }
                }
            }
        }
    }
}