package com.ljmaq.budgetrule.features.onboarding.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun OnBoardingScreen(
    navController: NavController,
    viewModel: OnBoardingViewModel = hiltViewModel()
) {
    Box {
        Button(onClick = {
            viewModel.saveOnBoardingState(completed = true)
            navController.popBackStack()
        }) {
            Text(text = "Done")
        }
    }
}