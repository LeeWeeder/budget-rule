package com.ljmaq.budgetrule.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ljmaq.budgetrule.features.record.domain.model.Income
import com.ljmaq.budgetrule.features.record.presentation.home.HomeEvent
import com.ljmaq.budgetrule.features.record.presentation.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlertDialog(
    onDismissRequest: () -> Unit,
    supportingText: String,
    dismissButton: @Composable (() -> Unit),
    confirmButton: @Composable (() -> Unit)
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.extraLarge
            )
            .padding(top = 24.dp, end = 20.dp, bottom = 16.dp, start = 24.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = AbsoluteRoundedCornerShape(28.dp)
            )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = supportingText,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                modifier = Modifier.fillMaxWidth()
            ) {
                dismissButton.invoke()
                confirmButton.invoke()
            }
        }
    }
}

@Composable
fun DeleteWarningAlertDialog(
    onDismissRequest: () -> Unit,
    selectedRecords: SnapshotStateList<Income>,
    viewModel: HomeViewModel,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    supportingText: String
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        supportingText = supportingText,
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onDismissRequest.invoke()
                selectedRecords.forEach { income ->
                    viewModel.onEvent(HomeEvent.DeleteIncome(income))
                }
                scope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = "Record deleted",
                        actionLabel = "Undo",
                        duration = SnackbarDuration.Long
                    )

                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(HomeEvent.RestoreIncome)
                        snackbarHostState.showSnackbar(
                            message = "Record restored",
                            withDismissAction = true
                        )
                    }
                }
                viewModel.onEvent(HomeEvent.ChangeSelectionMode)
            }) {
                Text(text = "Delete")
            }
        }
    )
}