package com.ljmaq.budgetrule.features.record.presentation.records.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ljmaq.budgetrule.R
import com.ljmaq.budgetrule.features.record.domain.model.Category
import com.ljmaq.budgetrule.features.record.presentation.records.util.Formatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryItem(
    category: Category,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = { },
        colors = CardDefaults.cardColors(
            contentColor = category.contentColor
        ),
        shape = MaterialTheme.shapes.extraSmall,
        modifier = modifier
    ) {
        CategoryItemContent(category = category, onClick = onClick)
    }
}

@Composable
private fun CategoryItemContent(category: Category, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Text(text = category.name, style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = Formatter.formatCurrency(category.amount.toString()),
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(12.dp), contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(id = R.drawable.history),
                        contentDescription = "Transaction history icon"
                    )
                }
                Text(
                    text = "12/12/2023",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            Text(
                text = (category.percentage * 100).toInt().toString(),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}