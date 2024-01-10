package com.ljmaq.budgetrule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.ljmaq.budgetrule.presentation.partition.MainActivityUiState
import com.ljmaq.budgetrule.presentation.partition.MainActivityUiState.LOADING
import com.ljmaq.budgetrule.presentation.partition.MainActivityUiState.SUCCESS
import com.ljmaq.budgetrule.presentation.partition.PartitionViewModel
import com.ljmaq.budgetrule.ui.theme.BudgetRuleTheme
import com.ljmaq.budgetrule.util.Screen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val partitionViewModel: PartitionViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiState: MainActivityUiState by mutableStateOf(LOADING)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                partitionViewModel.uiState
                    .onEach {
                        uiState = it
                    }
                    .collect()
            }
        }

        splashscreen.setKeepOnScreenCondition {
            when (uiState) {
                LOADING -> true
                SUCCESS -> false
            }
        }


        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            BudgetRuleTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetupNavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route
                    )
                }
            }
        }
    }
}