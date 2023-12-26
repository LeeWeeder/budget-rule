package com.ljmaq.budgetrule.presentation.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetRuleFilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    text: String,
    withCheckIcon: Boolean = true
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Text(text = text)
        },
        leadingIcon = {
            if (withCheckIcon) {
                AnimatedVisibility(
                    visible = selected,
                    enter = fadeIn() + expandIn(expandFrom = Alignment.Center)
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Check icon",
                        Modifier.size(FilterChipDefaults.IconSize)
                    )
                }
            }
        }
    )
}