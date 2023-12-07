package com.ljmaq.budgetrule.features.record.presentation.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ljmaq.budgetrule.R
import com.ljmaq.budgetrule.core.presentation.components.DeleteWarningAlertDialog
import com.ljmaq.budgetrule.features.record.domain.model.Partition
import com.ljmaq.budgetrule.features.record.presentation.home.components.BudgetRuleFilterChip
import com.ljmaq.budgetrule.features.record.presentation.home.components.BudgetRuleSheet
import com.ljmaq.budgetrule.features.record.presentation.home.components.Button
import com.ljmaq.budgetrule.features.record.presentation.home.components.CategoryItem
import com.ljmaq.budgetrule.features.record.presentation.home.components.DrawerSheetSubtitle
import com.ljmaq.budgetrule.features.record.presentation.home.components.GreetingsAppBar
import com.ljmaq.budgetrule.features.record.presentation.home.components.IncomeItem
import com.ljmaq.budgetrule.features.record.presentation.home.components.OnSelectionModeTopAppBar
import com.ljmaq.budgetrule.features.record.presentation.home.components.OutlinedButton
import com.ljmaq.budgetrule.features.record.presentation.home.components.SegmentedButtonValues
import com.ljmaq.budgetrule.features.record.presentation.home.components.SingleChoiceSegmentedButton
import com.ljmaq.budgetrule.features.record.presentation.util.Screen

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class
)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    createRecordViewModel: CreateRecordViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val partitionState = viewModel.partitionState.value
    val isOnSelectionMode = viewModel.isOnSelectionMode.value
    val selectedRecords = viewModel.selectedRecords
    val dialogState = viewModel.dialogState.value
    val createRecordSheetVisibilityState = viewModel.createRecordSheetState.value

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    BackHandler(enabled = isOnSelectionMode) {
        viewModel.onEvent(HomeEvent.ChangeSelectionMode)
    }

    Box {
        if (dialogState.deleteWarningDialog.state) {
            DeleteWarningAlertDialog(
                selectedRecords = selectedRecords,
                viewModel = viewModel,
                scope = scope,
                snackbarHostState = snackbarHostState,
                supportingText = "Delete selected records?",
                onDismissRequest = {
                    viewModel.onEvent(HomeEvent.DismissDialog(dialogState.deleteWarningDialog))
                }
            )
        }

        val greetingsTopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        val onSelectionModeTopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            modifier = Modifier
                .nestedScroll(greetingsTopAppBarScrollBehavior.nestedScrollConnection)
                .nestedScroll(onSelectionModeTopAppBarScrollBehavior.nestedScrollConnection),
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = {
                        Text(text = "Create record")
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.edit),
                            contentDescription = "Create icon"
                        )
                    },
                    onClick = {
                        viewModel.onEvent(HomeEvent.CreateRecord)
                        if (isOnSelectionMode) viewModel.onEvent(HomeEvent.ChangeSelectionMode)
                    })
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                if (isOnSelectionMode) {
                    OnSelectionModeTopAppBar(
                        selectedRecords = selectedRecords,
                        onNavigationIconButtonClick = {
                            viewModel.onEvent(HomeEvent.ChangeSelectionMode)
                        },
                        onEditIconButtonClick = {
                            navController.navigate(
                                Screen.SettingsScreen.route + "?recordId=${selectedRecords[0].id}"
                            )
                            viewModel.onEvent(HomeEvent.ChangeSelectionMode)
                        },
                        onDeleteIconButtonClick = {
                            viewModel.onEvent(HomeEvent.DeleteIconButtonClick)
                        },
                        onSelectAllIconButtonClick = {
                            viewModel.onEvent(HomeEvent.AddAllToSelection)
                        },
                        onUnselectAllIconButtonClick = {
                            viewModel.onEvent(HomeEvent.RemoveAllFromSelection)
                            viewModel.onEvent(HomeEvent.ChangeSelectionMode)
                        },
                        isAllRecordsSelected = state.incomes.size == selectedRecords.size,
                        scrollBehavior = onSelectionModeTopAppBarScrollBehavior
                    )
                } else {
                    GreetingsAppBar(
                        scrollBehavior = greetingsTopAppBarScrollBehavior,
                        navController = navController
                    )
                }
            }
        ) { paddingValues ->
            val cancelCreateRecord = {
                viewModel.onEvent(HomeEvent.CancelCreateRecord)
                createRecordViewModel.onEvent(CreateRecordEvent.ResetState)
            }
            if (createRecordSheetVisibilityState) {
                BudgetRuleSheet(
                    onDismissRequest = cancelCreateRecord
                ) {
                    val createRecordState = createRecordViewModel.state.value

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painterResource(id = R.drawable.description),
                            contentDescription = "Description icon"
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Create record", style = MaterialTheme.typography.titleLarge)
                    }
                    DrawerSheetSubtitle(text = "Record type")
                    SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                        SingleChoiceSegmentedButton(
                            text = "Income",
                            selected = createRecordState.selectedRecordType == RecordType.Income,
                            position = SegmentedButtonValues.START
                        ) {
                            createRecordViewModel.onEvent(
                                CreateRecordEvent.ChangeRecordType(
                                    RecordType.Income
                                )
                            )
                        }
                        SingleChoiceSegmentedButton(
                            text = "Expense",
                            selected = createRecordState.selectedRecordType == RecordType.Expense,
                            position = SegmentedButtonValues.END
                        ) {
                            createRecordViewModel.onEvent(
                                CreateRecordEvent.ChangeRecordType(
                                    RecordType.Expense
                                )
                            )
                        }
                    }
                    /*AnimatedContent(targetState = incomeSelected, label = "Description") {
                        if (it) {
                            Text(text = "Income")
                        } else {
                            Text(text = "Expense")
                        }
                    }*/
                    AnimatedVisibility(visible = createRecordState.selectedRecordType == RecordType.Expense) {
                        Column {
                            DrawerSheetSubtitle(text = "Partition selection")
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                item {
                                    BudgetRuleFilterChip(
                                        selected = createRecordState.selectedPartition == Partition.Needs(),
                                        onClick = {
                                            createRecordViewModel.onEvent(
                                                CreateRecordEvent.ChangePartition(
                                                    Partition.Needs()
                                                )
                                            )
                                        },
                                        text = Partition.Needs().name
                                    )
                                }
                                item {
                                    BudgetRuleFilterChip(
                                        selected = createRecordState.selectedPartition == Partition.Wants(),
                                        onClick = {
                                            createRecordViewModel.onEvent(
                                                CreateRecordEvent.ChangePartition(
                                                    Partition.Wants()
                                                )
                                            )
                                        },
                                        text = Partition.Wants().name
                                    )
                                }
                                item {
                                    BudgetRuleFilterChip(
                                        selected = createRecordState.selectedPartition == Partition.Savings(),
                                        onClick = {
                                            createRecordViewModel.onEvent(
                                                CreateRecordEvent.ChangePartition(
                                                    Partition.Savings()
                                                )
                                            )
                                        },
                                        text = Partition.Savings().name
                                    )
                                }
                                item {
                                    BudgetRuleFilterChip(
                                        selected = createRecordState.selectedPartition == Partition.Investments(),
                                        onClick = {
                                            createRecordViewModel.onEvent(
                                                CreateRecordEvent.ChangePartition(
                                                    Partition.Investments()
                                                )
                                            )
                                        },
                                        text = Partition.Investments().name
                                    )
                                }
                            }
                        }
                    }

                    DrawerSheetSubtitle(text = "Amount")
                    val focusRequester by remember {
                        mutableStateOf(FocusRequester())
                    }

                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }

                    TextField(
                        value = when (createRecordState.amount) {
                            "0." -> "."
                            "0" -> ""
                            else -> createRecordState.amount
                        },
                        onValueChange = {
                            createRecordViewModel.onEvent(CreateRecordEvent.UpdateAmount(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        placeholder = {
                            Text(text = "0")
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End)
                    ) {
                        OutlinedButton(
                            onClick = cancelCreateRecord,
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.cancel),
                                    contentDescription = "Cancel icon",
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                            }) {
                            Text(text = "Cancel")
                        }
                        Button(
                            onClick = {
                                createRecordViewModel.onEvent(CreateRecordEvent.InsertRecord)
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.note_add),
                                    contentDescription = "Add record icon",
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                            }
                        ) {
                            Text(text = "Create")
                        }
                    }
                }
            }
            /*if (dialogState.value.isAddExpenseRecordDialogOpen) {
                AddExpensesRecordSheet(
                    onDismissRequest = {
                        viewModel.onEvent(HomeEvent.PartitionDialogDismiss)
                    },
                    sheetState = sheetState,
                    category = category.selectedCategory!!,
                    paddingValues = paddingValues
                )
            }
            if (dialogState.value.isCreateRecordDialogOpen) CreateRecordDialog(
                snackbarHostState = snackbarHostState,
                scope = scope
            )
            if (dialogState.value.isEditRecordDialogOpen) viewModel.currentIncome?.let {
                EditRecordDialog(
                    snackbarHostState = snackbarHostState,
                    scope = scope,
                    record = it
                )
            }*/

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .consumeWindowInsets(paddingValues),
                contentPadding = paddingValues
            ) {
                item {
                    LazyRow {
                        item {
                            val partitions = listOf(
                                partitionState.needs,
                                partitionState.wants,
                                partitionState.savings,
                                partitionState.investments
                            )
                            partitions.forEachIndexed { index, partition ->
                                if (index == 0) {
                                    Spacer(modifier = Modifier.width(12.dp))
                                }
                                CategoryItem(partition = partition, onClick = {
                                    // TODO: Implement on click, navigate to category screen
                                })
                                if (index == partitions.lastIndex) {
                                    Spacer(modifier = Modifier.width(12.dp))
                                }
                            }
                        }
                    }

                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Income transactions",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                items(state.incomes) { income ->
                    IncomeItem(
                        income = income,
                        isSelected = selectedRecords.contains(income),
                        modifier = Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onClick = {
                                    if (!isOnSelectionMode) {
                                        viewModel.onEvent(HomeEvent.EditIncome(income = income))
                                    } else {
                                        if (selectedRecords.contains(income)) {
                                            viewModel.onEvent(
                                                HomeEvent.RemoveFromSelection(
                                                    income
                                                )
                                            )
                                        } else {
                                            viewModel.onEvent(HomeEvent.AddToSelection(income))
                                        }
                                        if (selectedRecords.size < 1) {
                                            viewModel.onEvent(HomeEvent.ChangeSelectionMode)
                                        }
                                    }
                                },
                                onLongClick = {
                                    if (isOnSelectionMode) {
                                        if (selectedRecords.contains(income)) {
                                            return@combinedClickable
                                        }
                                        viewModel.onEvent(HomeEvent.AddToSelection(income))
                                        var first = -1
                                        var last = -1
                                        state.incomes.forEachIndexed { index, record ->
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
                                            temp.addAll(state.incomes.subList(first, last))
                                            temp.forEach { record ->
                                                viewModel.onEvent(
                                                    HomeEvent.AddToSelection(
                                                        record
                                                    )
                                                )
                                            }
                                        } else {
                                            val temp = mutableListOf(selectedRecords.last())
                                            temp.addAll(state.incomes.subList(last, first))
                                            temp.forEach { record ->
                                                viewModel.onEvent(
                                                    HomeEvent.AddToSelection(
                                                        record
                                                    )
                                                )
                                            }
                                        }
                                    } else {
                                        viewModel.onEvent(HomeEvent.ChangeSelectionMode)
                                        viewModel.onEvent(HomeEvent.AddToSelection(income))
                                    }
                                }
                            ),
                        onIconClick = {
                            if (!selectedRecords.contains(income)) {
                                viewModel.onEvent(HomeEvent.AddToSelection(income))
                            } else {
                                viewModel.onEvent(HomeEvent.RemoveFromSelection(income))
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}