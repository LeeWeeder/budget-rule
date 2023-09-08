package com.ljmaq.budgetrule.features.record.presentation.records.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ljmaq.budgetrule.features.record.domain.model.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onCategoryItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isSelected) {
        Card(colors = CardDefaults.cardColors(category.color), modifier = modifier) {
            CategoryItemContent(name = category.name, amount = category.amount)
        }
    } else {
        OutlinedCard(onClick = onCategoryItemClick, modifier = modifier) {
            CategoryItemContent(name = category.name, amount = category.amount)
        }
    }
}

@Composable
private fun CategoryItemContent(name: String, amount: String) {
    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = name, style = MaterialTheme.typography.labelLarge, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Text(text = amount)
    }
}