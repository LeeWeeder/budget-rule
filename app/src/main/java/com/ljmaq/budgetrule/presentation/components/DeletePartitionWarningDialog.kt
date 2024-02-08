package com.ljmaq.budgetrule.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.ljmaq.budgetrule.R

@Composable
fun DeletePartitionWarningDialog(
    isShowing: Boolean,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    if (isShowing) {
        AlertDialog(onDismissRequest = onDismissRequest, confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(text = "Delete")
            }
        }, text = {
            Text(text = "This will move all the amount of this partition to Excess partition. ")
        }, title = {
            Text(text = "Delete selected partition?")
        }, icon = {
            Icon(
                painter = painterResource(id = R.drawable.warning),
                contentDescription = "Warning icon"
            )
        }, dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = "Cancel")
            }
        })
    }
}