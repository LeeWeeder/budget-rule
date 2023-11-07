package com.ljmaq.budgetrule.features.record.presentation.records

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ljmaq.budgetrule.R
import com.ljmaq.budgetrule.core.presentation.components.DeleteWarningAlertDialog
import com.ljmaq.budgetrule.features.record.domain.model.Category
import com.ljmaq.budgetrule.features.record.presentation.records.add_record.AddRecordDialog
import com.ljmaq.budgetrule.features.record.presentation.records.components.AddExpensesRecordSheet
import com.ljmaq.budgetrule.features.record.presentation.records.components.CategoryItem
import com.ljmaq.budgetrule.features.record.presentation.records.components.GreetingsAppBar
import com.ljmaq.budgetrule.features.record.presentation.records.components.OnSelectionModeTopAppBar
import com.ljmaq.budgetrule.features.record.presentation.records.components.RecordItem
import com.ljmaq.budgetrule.features.record.presentation.records.edit_record.EditRecordDialog
import com.ljmaq.budgetrule.features.record.presentation.util.Screen

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)
@Composable
fun RecordScreen(
    navController: NavController,
    viewModel: RecordsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val isOnSelectionMode = viewModel.isOnSelectionMode.value
    val selectedRecords = viewModel.selectedRecords

    val dialogState = viewModel.dialogState
    val isDeleteConfirmationOpen = remember {
        mutableStateOf(false)
    }

    val category = viewModel.selectedCategory.value

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    BackHandler(enabled = isOnSelectionMode) {
        viewModel.onEvent(RecordsEvent.ChangeSelectionMode)
    }

    Box {
        if (isDeleteConfirmationOpen.value) {
            DeleteWarningAlertDialog(
                isDeleteConfirmationOpen = isDeleteConfirmationOpen,
                selectedRecords = selectedRecords,
                viewModel = viewModel,
                scope = scope,
                snackbarHostState = snackbarHostState,
                supportingText = "Delete selected records?"
            )
        }

        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )

        val greetingsTopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        val onSelectionModeTopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            modifier = Modifier
                .nestedScroll(greetingsTopAppBarScrollBehavior.nestedScrollConnection)
                .nestedScroll(onSelectionModeTopAppBarScrollBehavior.nestedScrollConnection),
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = {
                        Text(text = "Create")
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.edit),
                            contentDescription = "Create icon"
                        )
                    },
                    onClick = {
                        viewModel.onEvent(RecordsEvent.CreateRecord)
                        if (isOnSelectionMode) viewModel.onEvent(RecordsEvent.ChangeSelectionMode)
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
                            viewModel.onEvent(RecordsEvent.ChangeSelectionMode)
                        },
                        onDeleteIconButtonClick = {
                            isDeleteConfirmationOpen.value = true
                        },
                        onSelectAllIconButtonClick = {
                            viewModel.onEvent(RecordsEvent.AddAllToSelection)
                        },
                        onUnselectAllIconButtonClick = {
                            viewModel.onEvent(RecordsEvent.RemoveAllFromSelection)
                            viewModel.onEvent(RecordsEvent.ChangeSelectionMode)
                        },
                        isAllRecordsSelected = state.records.size == selectedRecords.size,
                        scrollBehavior = onSelectionModeTopAppBarScrollBehavior
                    )
                } else {
                    GreetingsAppBar(greetingsTopAppBarScrollBehavior)
                }
            }
        ) { paddingValues ->
            if (dialogState.value.isAddExpenseRecordDialogOpen) {
                AddExpensesRecordSheet(
                    onDismissRequest = {
                        viewModel.onEvent(RecordsEvent.CategoryDialogDismiss)
                    },
                    sheetState = sheetState,
                    category = category.selectedCategory!!,
                    paddingValues = paddingValues
                )
            }
            if (dialogState.value.isAddRecordDialogOpen) AddRecordDialog(
                snackbarHostState = snackbarHostState,
                scope = scope
            )
            if (dialogState.value.isEditRecordDialogOpen) viewModel.currentRecord?.let {
                EditRecordDialog(
                    snackbarHostState = snackbarHostState,
                    scope = scope,
                    record = it
                )
            }
            val padding = 10.dp
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .consumeWindowInsets(paddingValues),
                contentPadding = paddingValues
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = padding),
                        verticalArrangement = Arrangement.spacedBy(padding)
                    ) {
                        fun onClick(category: Category) {
                            viewModel.onEvent(RecordsEvent.CategoryClick(category))
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            CategoryItem(
                                category = Category.Needs,
                                onClick = {
                                    onClick(Category.Needs)
                                },
                                modifier = Modifier.weight(0.5f)
                            )
                            CategoryItem(
                                category = Category.Wants,
                                onClick = {
                                    onClick(Category.Wants)
                                },
                                modifier = Modifier.weight(0.5f)
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            CategoryItem(
                                category = Category.Savings,
                                onClick = {
                                    onClick(Category.Savings)
                                },
                                modifier = Modifier.weight(0.5f)
                            )
                            CategoryItem(
                                category = Category.Investments,
                                onClick = {
                                    onClick(Category.Investments)
                                },
                                modifier = Modifier.weight(0.5f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Income transactions",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                items(state.records) { record ->
                    RecordItem(
                        record = record,
                        isSelected = selectedRecords.contains(record),
                        modifier = Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onClick = {
                                    if (!isOnSelectionMode) {
                                        viewModel.onEvent(RecordsEvent.EditRecord(record = record))
                                    } else {
                                        if (selectedRecords.contains(record)) {
                                            viewModel.onEvent(
                                                RecordsEvent.RemoveFromSelection(
                                                    record
                                                )
                                            )
                                        } else {
                                            viewModel.onEvent(RecordsEvent.AddToSelection(record))
                                        }
                                        if (selectedRecords.size < 1) {
                                            viewModel.onEvent(RecordsEvent.ChangeSelectionMode)
                                        }
                                    }
                                },
                                onLongClick = {
                                    if (isOnSelectionMode) {
                                        if (selectedRecords.contains(record)) {
                                            return@combinedClickable
                                        }
                                        viewModel.onEvent(RecordsEvent.AddToSelection(record))
                                        var first = -1
                                        var last = -1
                                        state.records.forEachIndexed { index, record ->
                                            if (selectedRecords.contains(record)) {
                                                if (first == -1 || index < first) {
                                                    first = index
                                                }
                                                if (last == -1 || index > last) {
                                                    last = index
                                                }
                                            }
                                        }

                                        if (first < last) {
                                            val temp = mutableListOf(selectedRecords.first())
                                            temp.addAll(state.records.subList(first, last))
                                            temp.forEach { record ->
                                                viewModel.onEvent(
                                                    RecordsEvent.AddToSelection(
                                                        record
                                                    )
                                                )
                                            }
                                        } else {
                                            val temp = mutableListOf(selectedRecords.last())
                                            temp.addAll(state.records.subList(last, first))
                                            temp.forEach { record ->
                                                viewModel.onEvent(
                                                    RecordsEvent.AddToSelection(
                                                        record
                                                    )
                                                )
                                            }
                                        }
                                    } else {
                                        viewModel.onEvent(RecordsEvent.ChangeSelectionMode)
                                        viewModel.onEvent(RecordsEvent.AddToSelection(record))
                                    }
                                }
                            ),
                        onIconClick = {
                            if (!selectedRecords.contains(record)) {
                                viewModel.onEvent(RecordsEvent.AddToSelection(record))
                            } else {
                                viewModel.onEvent(RecordsEvent.RemoveFromSelection(record))
                            }
                        }
                    )
                }
            }
        }
    }
}