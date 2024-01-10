package com.ljmaq.budgetrule.presentation.tune_share_percentage.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ljmaq.budgetrule.domain.model.Partition

@Composable
fun PartitionItem(
    partition: Partition,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLeftOverPartition: Boolean = false,
    onValueChange: (Float) -> Unit
) {
    if (isLeftOverPartition) {
        PartitionItemContent(
            partition = partition,
            onValueChange = onValueChange,
            enabled = enabled,
            modifier = modifier
        )
    } else {
        Card(
            colors = CardDefaults.cardColors(
                MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            PartitionItemContent(
                partition = partition,
                onValueChange = onValueChange,
                enabled = enabled
            )
        }
    }
}

@Composable
private fun PartitionItemContent(
    partition: Partition,
    onValueChange: (Float) -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(top = 12.dp)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = partition.name, style = MaterialTheme.typography.labelLarge)
            Text(text = "${(partition.sharePercent * 100).toInt()}%")
        }
        Slider(
            value = partition.sharePercent,
            onValueChange = onValueChange,
            steps = 100,
            colors = SliderDefaults.colors(
                activeTickColor = Color.Transparent,
                inactiveTickColor = Color.Transparent,
                inactiveTrackColor = MaterialTheme.colorScheme.onSecondary,
            ),
            enabled = enabled
        )
    }
}