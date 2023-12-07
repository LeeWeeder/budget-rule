package com.ljmaq.budgetrule.features.record.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ljmaq.budgetrule.features.record.domain.model.Partition
import com.ljmaq.budgetrule.features.record.presentation.home.util.Formatter

@Composable
fun CategoryItem(
    partition: Partition,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row {
        Spacer(modifier = Modifier.width(4.dp))
        ElevatedCard(
            onClick = onClick,
            colors = CardDefaults.cardColors(
                contentColor = partition.contentColor,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            ),
            modifier = modifier.width(130.dp)
        ) {
            CategoryItemContent(partition = partition)
        }
        Spacer(modifier = Modifier.width(4.dp))
    }
}

@Composable
private fun CategoryItemContent(partition: Partition) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = partition.name, style = MaterialTheme.typography.labelSmall)
            Text(
                text = "${((partition.partitionValue) * 100).toInt()}%",
                style = MaterialTheme.typography.labelSmall
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = Formatter.formatCurrency(partition.amount.toString(), "PHP"),
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 16.sp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Right
        )
    }
}