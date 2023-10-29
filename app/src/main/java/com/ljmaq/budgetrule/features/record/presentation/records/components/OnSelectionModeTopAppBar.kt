package com.ljmaq.budgetrule.features.record.presentation.records.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.SelectAll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.ljmaq.budgetrule.features.record.domain.model.Record

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnSelectionModeTopAppBar(
    selectedRecords: SnapshotStateList<Record>,
    onNavigationIconButtonClick: () -> Unit,
    onEditIconButtonClick: () -> Unit,
    onDeleteIconButtonClick: () -> Unit,
    onSelectAllIconButtonClick: () -> Unit,
    onUnselectAllIconButtonClick: () -> Unit,
    isAllRecordsSelected: Boolean
) {
    val numberOfItemSelected = selectedRecords.size.toString()
    TopAppBar(
        title = { Text(numberOfItemSelected) },
        navigationIcon = {
            IconButton(onClick = onNavigationIconButtonClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back icon")
            }
        },
        actions = {
            if (numberOfItemSelected == "1") {
                IconButton(onClick = onEditIconButtonClick) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit icon")
                }
            }
            IconButton(onClick = if (isAllRecordsSelected) onUnselectAllIconButtonClick else onSelectAllIconButtonClick) {
                Icon(
                    imageVector = Icons.Default.SelectAll,
                    contentDescription = "Select all icon"
                )
            }
            IconButton(onClick = onDeleteIconButtonClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete icon")
            }
        }
    )
}