package com.ljmaq.budgetrule.features.record.presentation.records

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ljmaq.budgetrule.features.record.domain.model.Category
import com.ljmaq.budgetrule.features.record.presentation.records.add_record.AddRecordDialog
import com.ljmaq.budgetrule.features.record.presentation.records.components.CategoryItem
import com.ljmaq.budgetrule.features.record.presentation.records.components.GreetingsAppBar
import com.ljmaq.budgetrule.features.record.presentation.records.components.OnSelectionModeTopAppBar
import com.ljmaq.budgetrule.features.record.presentation.records.components.RecordItem
import com.ljmaq.budgetrule.features.record.presentation.util.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(
    navController: NavController,
    viewModel: RecordsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val categoryState = viewModel.categoryState.value
    val isOnSelectionMode = viewModel.isOnSelectionMode.value
    val selectedRecords = viewModel.selectedRecords
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    val addRecordDialogState = viewModel.isAddRecordDialogShowing.value
    val isDeleteConfirmationOpen = remember {
        mutableStateOf(false)
    }

    Box {
        if (isDeleteConfirmationOpen.value) {
            AlertDialog(onDismissRequest = { isDeleteConfirmationOpen.value = false },
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.extraLarge
                    )
                    .padding(top = 24.dp, end = 20.dp, bottom = 10.dp, start = 24.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = AbsoluteRoundedCornerShape(28.dp)
                    )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Delete selected records?", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End), modifier = Modifier.fillMaxWidth()) {
                        TextButton(onClick = {
                            isDeleteConfirmationOpen.value = false
                        }) {
                            Text(text = "Cancel")
                        }
                        TextButton(onClick = {
                            isDeleteConfirmationOpen.value = false
                            selectedRecords.forEach { record ->
                                viewModel.onEvent(RecordsEvent.DeleteRecord(record))
                                viewModel.onEvent(RecordsEvent.ChangeSelectionMode)
                            }
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "${if (selectedRecords.size > 1) "Records" else "Record"} deleted",
                                    actionLabel = "Undo",
                                    duration = SnackbarDuration.Long
                                )

                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(RecordsEvent.RestoreRecord)
                                    viewModel.onEvent(RecordsEvent.ChangeSelectionMode)
                                    snackbarHostState.showSnackbar(
                                        message = "${if (selectedRecords.size > 1) "Records" else "Record"} restored",
                                        withDismissAction = true
                                    )
                                }

                                if (result == SnackbarResult.Dismissed) {
                                    viewModel.onEvent(RecordsEvent.ResetRecentlyDeletedRecord)
                                }
                            }
                        }) {
                            Text(text = "Delete")
                        }
                    }
                }
            }
        }
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = {
                        Text(text = "Create record")
                    },
                    icon = {
                        Icon(imageVector = Icons.Rounded.Create, contentDescription = "Create icon")
                    },
                    onClick = {
                        viewModel.onEvent(RecordsEvent.CreateRecord)
                    })
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                if (isOnSelectionMode) {
                    OnSelectionModeTopAppBar(
                        selectedRecords = selectedRecords,
                        onNavigationIconButtonClick = {
                            viewModel.onEvent(RecordsEvent.ChangeSelectionMode)
                        },
                        onEditIconButtonClick = {
                            navController.navigate(
                                Screen.EditRecordScreen.route + "?recordId=${selectedRecords[0].id}"
                            )
                        },
                        onDeleteIconButtonClick = {
                            isDeleteConfirmationOpen.value = true
                        }
                    )
                } else {
                    GreetingsAppBar()
                }
            }) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(top = 12.dp)
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Category.categories.forEach { category ->
                        CategoryItem(
                            category = category,
                            modifier = Modifier.width(100.dp),
                            isSelected = categoryState.selectedCategory == Category.categories.indexOf(
                                category
                            ),
                            onCategoryItemClick = {
                                viewModel.onEvent(RecordsEvent.ChangeCategory(category))
                            })
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Records",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.records) { record ->
                        RecordItem(record = record, modifier = Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onClick = {
                                    if (isOnSelectionMode) {
                                        viewModel.onEvent(RecordsEvent.AddToSelection(record))
                                        if (selectedRecords.size < 1) {
                                            viewModel.onEvent(RecordsEvent.ChangeSelectionMode)
                                        }
                                    } else {
                                        navController.navigate(
                                            Screen.EditRecordScreen.route + "?recordId=${record.id}"
                                        )
                                    }
                                },
                                onLongClick = {
                                    viewModel.onEvent(RecordsEvent.ChangeSelectionMode)
                                    viewModel.onEvent(RecordsEvent.AddToSelection(record))
                                }
                            )
                            .background(if (selectedRecords.contains(record)) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface)
                            )
                    }
                }
            }
        }
    }
    if (addRecordDialogState) AddRecordDialog(snackbarHostState = snackbarHostState, scope = scope)
}