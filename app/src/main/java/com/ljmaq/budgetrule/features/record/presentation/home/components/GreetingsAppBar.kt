package com.ljmaq.budgetrule.features.record.presentation.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ljmaq.budgetrule.features.record.presentation.home.util.getGreetings
import com.ljmaq.budgetrule.features.record.presentation.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GreetingsAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController
) {
    TopAppBar(title = {
        Text(text = getGreetings())
    }, scrollBehavior = scrollBehavior, actions = {
        IconButton(onClick = { navController.navigate(Screen.SettingsScreen.route) }) {
            /* TODO: Replace icon with the outlined Material Symbol alternative */
            Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings icon")
        }
    })
}