package com.ljmaq.budgetrule.features.record.presentation.home.add_record.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ButtonRow(modifier: Modifier = Modifier, content: @Composable (RowScope.() -> Unit)) {
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier.fillMaxWidth()) {
        content.invoke(this)
    }
}