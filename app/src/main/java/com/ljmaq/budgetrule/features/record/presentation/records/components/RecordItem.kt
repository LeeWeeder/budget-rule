package com.ljmaq.budgetrule.features.record.presentation.records.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ljmaq.budgetrule.features.record.domain.model.Record
import com.ljmaq.budgetrule.features.record.presentation.records.util.Formatter

@Composable
fun RecordItem(
    record: Record,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    Column {
        Box(
            modifier = modifier.background(MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Text(
                        text = Formatter.formatCurrency(record.amount),
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (record.amount.toDouble() < 0) {
                            Color(0xFFCC0202)
                        } else {
                            Color(0xFF4BB543)
                        },
                        maxLines = 1
                    )
                    Text(text = Formatter.formatDate(record.timestamp))
                }
            }
        }
        Divider()
    }
}

@Preview
@Composable
fun Preview() {
    Column {
        RecordItem(record = Record(timestamp = 1L, amount = "100.00", isExpenses = false)) {

        }
        RecordItem(record = Record(timestamp = 1L, amount = "100.00", isExpenses = false)) {

        }
    }
}