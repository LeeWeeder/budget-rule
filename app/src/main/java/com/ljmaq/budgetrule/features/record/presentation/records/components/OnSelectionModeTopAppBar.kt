package com.ljmaq.budgetrule.features.record.presentation.records.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.res.painterResource
import com.ljmaq.budgetrule.R
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
                Icon(painter = painterResource(id = R.drawable.arrow_back), contentDescription = "Back icon")
            }
        },
        actions = {
            if (numberOfItemSelected == "1") {
                IconButton(onClick = onEditIconButtonClick) {
                    Icon(painter = painterResource(id = R.drawable.edit), contentDescription = "Edit icon")
                }
            }
            IconButton(onClick = if (isAllRecordsSelected) onUnselectAllIconButtonClick else onSelectAllIconButtonClick) {
                Icon(
                    painter = painterResource(id = if (isAllRecordsSelected) R.drawable.deselect else R.drawable.select_all),
                    contentDescription = "Select all icon"
                )
            }
            IconButton(onClick = onDeleteIconButtonClick) {
                Icon(painter = painterResource(id = R.drawable.delete), contentDescription = "Delete icon")
            }
        }
    )
}