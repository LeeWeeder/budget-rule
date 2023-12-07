package com.ljmaq.budgetrule.features.record.presentation.home.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Button(
    onClick: () -> Unit,
    leadingIcon: @Composable () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Button(onClick = onClick, contentPadding = ButtonDefaults.ButtonWithIconContentPadding) {
        leadingIcon()
        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
        content()
    }
}

@Composable
fun OutlinedButton(
    onClick: () -> Unit,
    leadingIcon: @Composable () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    OutlinedButton(onClick = onClick, contentPadding = ButtonDefaults.ButtonWithIconContentPadding) {
        leadingIcon()
        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
        content()
    }
}