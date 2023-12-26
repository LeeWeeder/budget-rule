package com.ljmaq.budgetrule.util

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalDensity

fun String.isDigitsOnly(): Boolean {
    if (this.isEmpty()) return true
    if (this.contains('-')) return false
    return try {
        this.toBigDecimal()
        true
    } catch (e: NumberFormatException) {
        false
    }
}

@Composable
fun keyBoardAsState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}