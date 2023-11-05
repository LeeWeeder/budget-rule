package com.ljmaq.budgetrule.features.record.presentation.records.components

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ljmaq.budgetrule.features.record.domain.model.Category

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddExpensesRecordSheet(
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    category: Category,
    modifier: Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = modifier.fillMaxHeight()
    ) {
        Text(text = category.amount.toString())
        FilledTonalButton(onClick = { }) {
            Text(text = "Save")
        }
    }
}