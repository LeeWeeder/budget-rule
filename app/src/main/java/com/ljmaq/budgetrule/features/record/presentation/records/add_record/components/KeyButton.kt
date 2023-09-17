package com.ljmaq.budgetrule.features.record.presentation.records.add_record.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun KeyButton(
    number: String,
    onClick: () -> Unit
) {
    Button(onClick = onClick, colors = ButtonDefaults.outlinedButtonColors(), modifier = Modifier
        .width(85.dp)
        .height(70.dp), shape = MaterialTheme.shapes.extraLarge) {
        Text(text = number, style = MaterialTheme.typography.displaySmall, textAlign = TextAlign.Center)
    }
}

@Composable
fun KeyButton(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit
) {
    Button(onClick = onClick, colors = ButtonDefaults.outlinedButtonColors(), modifier = Modifier
        .width(85.dp)
        .height(70.dp), shape = MaterialTheme.shapes.extraLarge) {
        Icon(imageVector = icon, contentDescription = contentDescription)
    }
}

@Composable
fun KeyButton(
    operation: Char,
    onClick: () -> Unit
) {
    Button(onClick = onClick, colors = ButtonDefaults.filledTonalButtonColors(), modifier = Modifier
        .width(85.dp)
        .height(55.dp), shape = MaterialTheme.shapes.extraLarge) {
        Text(text = operation.toString(), style = MaterialTheme.typography.displaySmall, textAlign = TextAlign.Center)
    }
}