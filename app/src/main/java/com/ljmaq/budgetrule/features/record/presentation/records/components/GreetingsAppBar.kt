package com.ljmaq.budgetrule.features.record.presentation.records.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.ljmaq.budgetrule.features.record.presentation.records.util.getGreetings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GreetingsAppBar() {
    TopAppBar(title = {
        Text(text = getGreetings())
    })
}