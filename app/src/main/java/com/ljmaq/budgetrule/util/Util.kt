package com.ljmaq.budgetrule.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.core.content.ContextCompat

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

@Composable
fun dpToSp(value: Dp): TextUnit {
    with(LocalDensity.current) {
        return value.toSp()
    }
}

fun toImageBitmap(context: Context, @DrawableRes drawableRes: Int): ImageBitmap {
    val db = ContextCompat.getDrawable(context, drawableRes)

    val bitmap = Bitmap.createBitmap(
        db!!.intrinsicWidth, db.intrinsicHeight, Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)

    db.setBounds(0, 0, canvas.width, canvas.height)

    db.draw(canvas)

    return bitmap.asImageBitmap()
}