package com.ljmaq.budgetrule.presentation.home.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.ljmaq.budgetrule.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetRuleAppBar(
    onTuneIconClick: () -> Unit
) {
    LargeTopAppBar(title = {
        Text(text = "Budget Rule")
    }, actions = {
        IconButton(onClick = onTuneIconClick) {
            Icon(painter = painterResource(id = R.drawable.tune), contentDescription = "Tune icon")
        }
    })
}