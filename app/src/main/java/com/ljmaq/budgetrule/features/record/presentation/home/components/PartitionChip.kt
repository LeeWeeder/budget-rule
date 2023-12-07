package com.ljmaq.budgetrule.features.record.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ljmaq.budgetrule.features.record.domain.model.Partition

@Composable
fun PartitionChip(
    onClick: () -> Unit,
    partition: Partition
) {
    Box(
        modifier = Modifier
            .background(
                shape = MaterialTheme.shapes.small,
                color = Color.Transparent
            )
            .clip(MaterialTheme.shapes.small)
            .clickable {
                onClick.invoke()
            }
            .border(
                width = 1.dp,
                shape = MaterialTheme.shapes.small,
                color = partition.contentColor
            )

            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = "${partition.name}: ${partition.amount}", style = MaterialTheme.typography.labelSmall)
    }
}