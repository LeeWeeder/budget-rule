package com.ljmaq.budgetrule.features.record.presentation.home.add_record.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

val modifier = Modifier.width(120.dp)
@Composable
fun KeyButton(
    number: String,
    onClick: () -> Unit
) {
    Button(onClick = onClick, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f), contentColor = MaterialTheme.colorScheme.onSurfaceVariant), shape = MaterialTheme.shapes.extraLarge, modifier = modifier) {
        Text(text = number, style = MaterialTheme.typography.displaySmall, textAlign = TextAlign.Center)
    }
}

@Composable
fun KeyButton(
    icon: Painter,
    contentDescription: String?,
    onClick: () -> Unit
) {
    Button(onClick = onClick, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary, contentColor = MaterialTheme.colorScheme.onTertiary), shape = MaterialTheme.shapes.extraLarge, modifier = modifier.fillMaxHeight(0.75f)) {
        Icon(painter = icon, contentDescription = contentDescription)
    }
}

@Composable
fun KeyButton(
    onClick: () -> Unit
) {
    Button(onClick = onClick, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f), contentColor = MaterialTheme.colorScheme.onSurfaceVariant), shape = MaterialTheme.shapes.extraLarge, modifier = modifier) {
        Text(text = ".", style = MaterialTheme.typography.displaySmall, textAlign = TextAlign.Center)
    }
}