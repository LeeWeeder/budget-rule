package com.ljmaq.budgetrule.features.record.presentation.records.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ljmaq.budgetrule.features.record.domain.model.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpensesRecordSheet(
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    category: Category
) {
    ModalBottomSheet(onDismissRequest = onDismissRequest, sheetState = sheetState) {
        Text(text = category.amount.toString())
        FilledTonalButton(onClick = { }) {
            Text(text = "Save")
        }
    }
}