package com.ljmaq.budgetrule.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetRuleAppBar(
    title: @Composable () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.mediumTopAppBarColors(scrolledContainerColor = TopAppBarDefaults.mediumTopAppBarColors().containerColor)
) {
    TopAppBar(
        title = title, actions = actions, navigationIcon = navigationIcon,
        colors = colors, scrollBehavior = scrollBehavior
    )
}