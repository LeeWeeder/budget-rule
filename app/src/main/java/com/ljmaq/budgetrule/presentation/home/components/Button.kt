package com.ljmaq.budgetrule.presentation.home.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BudgetRuleButton(
    onClick: () -> Unit,
    leadingIcon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Button(onClick = onClick, contentPadding = ButtonDefaults.ButtonWithIconContentPadding, modifier = modifier) {
        leadingIcon()
        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
        content()
    }
}

@Composable
fun BudgetRuleOutlinedButton(
    onClick: () -> Unit,
    leadingIcon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    content: @Composable RowScope.() -> Unit
) {
    OutlinedButton(onClick = onClick, contentPadding = ButtonDefaults.ButtonWithIconContentPadding, modifier = modifier, colors = colors) {
        leadingIcon()
        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
        content()
    }
}