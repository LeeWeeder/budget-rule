package com.ljmaq.budgetrule.features.record.presentation.records.add_record.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TabItem(selected: Boolean, onClick: () -> Unit, text: String) {
    Tab(
        selected = selected, onClick = onClick, modifier = Modifier
            .clip(MaterialTheme.shapes.extraLarge)
            .background(if (selected) MaterialTheme.colorScheme.secondary else Color.Transparent)
    ) {
        Text(text = text.uppercase(), modifier = Modifier.padding(15.dp), color = if (selected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.primary)
    }
}