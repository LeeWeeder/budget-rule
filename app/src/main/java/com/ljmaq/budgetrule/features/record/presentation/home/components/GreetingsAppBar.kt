package com.ljmaq.budgetrule.features.record.presentation.home.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import com.ljmaq.budgetrule.features.record.presentation.home.util.getGreetings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GreetingsAppBar(
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(title = {
        Text(text = getGreetings())
    }, scrollBehavior = scrollBehavior)
}