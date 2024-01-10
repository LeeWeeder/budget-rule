package com.ljmaq.budgetrule.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ljmaq.budgetrule.R
import com.ljmaq.budgetrule.domain.model.Partition
import com.ljmaq.budgetrule.presentation.components.BudgetRuleAppBar
import com.ljmaq.budgetrule.presentation.home.components.BudgetRuleButton
import com.ljmaq.budgetrule.presentation.home.components.BudgetRuleOutlinedButton
import com.ljmaq.budgetrule.presentation.home.components.BudgetRuleSheet
import com.ljmaq.budgetrule.presentation.home.components.DrawerSheetSubtitle
import com.ljmaq.budgetrule.presentation.home.components.PartitionItem
import com.ljmaq.budgetrule.presentation.home.components.SegmentedButtonValues
import com.ljmaq.budgetrule.presentation.home.components.SingleChoiceSegmentedButton
import com.ljmaq.budgetrule.presentation.home.util.Formatter
import com.ljmaq.budgetrule.presentation.partition.PartitionViewModel
import com.ljmaq.budgetrule.util.Screen

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    partitionViewModel: PartitionViewModel = hiltViewModel(),
    createRecordViewModel: CreateRecordViewModel = hiltViewModel()
) {
    val createRecordSheetVisibilityState = viewModel.createRecordSheetState.value
    val dialogState = viewModel.dialogState.value
    val partitionState = partitionViewModel.partitionState.value

    val balanceState = viewModel.balanceState.value
    val leftOverPartitionState = partitionViewModel.excessPartitionState.collectAsState()

    Scaffold(
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
                }
            )
        },
        topBar = {
            BudgetRuleAppBar(title = {
                Text(
                    text = "Budget Rule"
                )
            }, actions = {
                IconButton(onClick = {
                    navController.navigate(Screen.TuneSharePercentageScreen.route)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.tune),
                        contentDescription = "Tune icon"
                    )
                }
            })
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
                AnimatedVisibility(visible = createRecordState.selectedRecordType == RecordType.Expense) {
                    Column {
                        DrawerSheetSubtitle(text = "Partition selection")
                        /*LazyRow(
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
                        }*/
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
                        createRecordViewModel.onEvent(
                            CreateRecordEvent.UpdateAmount(
                                it
                            )
                        )
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
                    BudgetRuleOutlinedButton(
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
                    BudgetRuleButton(
                        onClick = {
                            createRecordViewModel.onEvent(CreateRecordEvent.InsertRecord)
                            createRecordViewModel.onEvent(CreateRecordEvent.ResetState)
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
        val partition = viewModel.currentPartition.value
        if (dialogState.newPartitionDialogShowing) {
            val focusRequester = remember {
                FocusRequester()
            }
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
            var partitionName by remember {
                mutableStateOf("")
            }
            var isError by remember {
                mutableStateOf(false)
            }
            AlertDialog(onDismissRequest = {
                viewModel.onEvent(HomeEvent.HideNewPartitionDialog)
            }, confirmButton = {
                Button(onClick = {
                    isError = partitionName.isEmpty()
                    if (!isError)
                        viewModel.onEvent(
                            HomeEvent.InsertPartition(
                                Partition(name = partitionName, amount = 0.0, sharePercent = 0f)
                            )
                        )
                    navController.navigate(Screen.TuneSharePercentageScreen.route)
                    viewModel.onEvent(HomeEvent.HideNewPartitionDialog)
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Next")
                }
            }, text = {
                Column {
                    OutlinedTextField(value = partitionName, onValueChange = {
                        partitionName = it
                        isError = false
                    }, label = {
                        Text(text = "Partition name")
                    }, modifier = Modifier.focusRequester(focusRequester),
                        isError = isError
                    )
                }
            }, title = {
                Text(text = "New partition")
            })
        }
        if (dialogState.updatePartitionDialogShowing) {
            val focusRequester = remember {
                FocusRequester()
            }
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
            var partitionName by remember {
                mutableStateOf(
                    TextFieldValue(
                        text = partition!!.name,
                        selection = TextRange(start = 0, end = partition.name.length)
                    )
                )
            }
            var isError by remember {
                mutableStateOf(false)
            }
            AlertDialog(onDismissRequest = {
                viewModel.onEvent(HomeEvent.CancelEditPartitionName)
            }, confirmButton = {
                Button(onClick = {
                    isError = partitionName.text.isEmpty()
                    if (!isError)
                        viewModel.onEvent(
                            HomeEvent.UpdatePartition(
                                Partition(
                                    id = partition!!.id,
                                    name = partitionName.text,
                                    amount = partition.amount,
                                    sharePercent = partition.sharePercent
                                )
                            )
                        )
                    viewModel.onEvent(HomeEvent.CancelEditPartitionName)
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Done")
                }
            }, text = {
                Column {
                    OutlinedTextField(value = partitionName, onValueChange = {
                        partitionName = it
                        isError = false
                    }, label = {
                        Text(text = "Partition name")
                    }, modifier = Modifier.focusRequester(focusRequester),
                        isError = isError
                    )
                }
            }, title = {
                Text(text = "Edit partition")
            })
        }
        if (dialogState.deletePartitionWarningDialogShowing) {
            AlertDialog(onDismissRequest = {
                viewModel.onEvent(HomeEvent.HideDeletePartitionDialog)
            }, confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(HomeEvent.DeletePartition(partition!!))
                        viewModel.onEvent(HomeEvent.HideDeletePartitionDialog)
                    }) {
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
                TextButton(onClick = {
                    viewModel.onEvent(HomeEvent.HideDeletePartitionDialog)
                }) {
                    Text(text = "Cancel")
                }
            })
        }
        val horizontalPadding = 16.dp
        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp + horizontalPadding)
                    .padding(top = 5.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inversePrimary),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 2.dp, top = 16.dp, bottom = 18.dp)
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.balance),
                        contentDescription = "Balance icon"
                    )
                    Column {
                        Text(
                            text = Formatter.formatCurrency(
                                amount = balanceState,
                                isoCode = "PHP"
                            ), style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.height(1.dp))
                        Text(
                            text = "Overall balance",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider()
            Column(
                modifier = Modifier.padding(horizontal = horizontalPadding)
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(
                        bottom = paddingValues.calculateBottomPadding(),
                        top = 16.dp
                    ),
                    horizontalAlignment = Alignment.End
                ) {
                    item {
                        ElevatedButton(
                            onClick = {
                                viewModel.onEvent(HomeEvent.ShowNewPartitionDialog)
                            }, modifier = Modifier,
                            contentPadding = ButtonDefaults.ButtonWithIconContentPadding
                        ) {
                            Icon(
                                painterResource(id = R.drawable.add),
                                contentDescription = "Add icon",
                                modifier = Modifier.size(ButtonDefaults.IconSize)
                            )
                            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                            Text(text = "New partition")
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    item {
                        PartitionItem(
                            partition = leftOverPartitionState.value
                        )
                        Spacer(modifier = Modifier.height(7.dp))
                    }

                    items(
                        items = partitionState.partitionList,
                        key = {
                            it.id!!
                        }
                    ) { partition ->
                        var isMenuExpanded by remember {
                            mutableStateOf(false)
                        }
                        var offset by remember {
                            mutableStateOf(Offset.Zero)
                        }
                        PartitionItem(
                            partition = partition, isMenuExpanded = isMenuExpanded,
                            viewModel = viewModel,
                            onDismissRequest = {
                                isMenuExpanded = false
                            }, offset = offset,
                            modifier = Modifier
                                .pointerInteropFilter {
                                    offset = Offset(x = it.x, y = it.y)
                                    println(offset.x)
                                    println(offset.y)
                                    false
                                }
                                .combinedClickable(
                                    onClick = { },
                                    onLongClick = {
                                        isMenuExpanded = true
                                    }
                                )
                        )
                        Spacer(modifier = Modifier.height(7.dp))
                    }
                }
            }
        }
    }
}