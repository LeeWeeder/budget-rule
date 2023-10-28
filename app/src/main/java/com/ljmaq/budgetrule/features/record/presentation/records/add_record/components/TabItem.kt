package com.ljmaq.budgetrule.features.record.presentation.records.add_record.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun TabItem(selected: Boolean, onClick: () -> Unit, text: String, modifier: Modifier = Modifier) {
    Tab(
        selected = selected, onClick = onClick, modifier = modifier
            .clip(MaterialTheme.shapes.extraLarge)
            .background(if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.primary)
            .border(1.dp, MaterialTheme.colorScheme.onPrimary, MaterialTheme.shapes.extraLarge)
    ) {
        Text(text = text.uppercase(), modifier = Modifier.padding(15.dp), color = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onPrimary)
    }
}