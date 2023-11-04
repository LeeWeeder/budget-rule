package com.ljmaq.budgetrule.features.record.presentation.records.edit_record

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.ljmaq.budgetrule.R
import com.ljmaq.budgetrule.features.record.domain.model.Record
import com.ljmaq.budgetrule.features.record.presentation.records.RecordsEvent
import com.ljmaq.budgetrule.features.record.presentation.records.RecordsViewModel
import com.ljmaq.budgetrule.features.record.presentation.records.add_record.components.TabItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRecordScreen(
    navController: NavController,
    editRecordViewModel: EditRecordViewModel = hiltViewModel(),
    recordsViewModel: RecordsViewModel = hiltViewModel()
) {
    val typeIsExpenses = editRecordViewModel.typeIsExpenses.value
    val amountState = editRecordViewModel.recordAmount.value
    val tabState = editRecordViewModel.tabState.value

    val isDeleteConfirmationOpen = remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(key1 = true) {
       editRecordViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is EditRecordViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is EditRecordViewModel.UiEvent.SaveRecord -> {

                }
            }
        }
    }

    Box {
        if (isDeleteConfirmationOpen.value) {
            DeleteWarningAlertDialog(
                isDeleteConfirmationOpen = isDeleteConfirmationOpen,
                recordsViewModel = recordsViewModel,
                editRecordViewModel = editRecordViewModel,
                navController = navController,
                scope = scope
            )
        }
        Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }, topBar = {

        }) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                TabRow(selectedTabIndex = tabState.index) {
                    Tab(
                        selected = !typeIsExpenses, onClick = {
                            editRecordViewModel.onEvent(
                                EditRecordEvent.ChangeRecordType
                            )
                        }) {
                        Text(text = "Income")
                    }
                    Tab(selected = typeIsExpenses, onClick = {
                        editRecordViewModel.onEvent(
                            EditRecordEvent.ChangeRecordType
                        )
                    }) {
                        Text(text = "Expenses")
                    }
                }
                Row(modifier = Modifier.fillMaxWidth()) {

                }
            }
        }
    }
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRecordDialog(
    editRecordViewModel: EditRecordViewModel = hiltViewModel(),
    recordsViewModel: RecordsViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    record: Record
) {
    val typeIsExpenses = record.isExpenses
    val amountState = record.amount
    val thisSnackbarHostState = remember {
        SnackbarHostState()
    }

    val isAlertDialogOpen = remember { mutableStateOf(false) }
    val isDeleteConfirmationOpen = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        editRecordViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is EditRecordViewModel.UiEvent.ShowSnackbar -> {
                    thisSnackbarHostState.showSnackbar(event.message)
                }

                is EditRecordViewModel.UiEvent.SaveRecord -> {
                    recordsViewModel.onEvent(RecordsEvent.CreateRecord)
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Record saved",
                            withDismissAction = true
                        )
                    }
                }

                is EditRecordViewModel.UiEvent.ShowAmountWarningDialog -> {
                    isAlertDialogOpen.value = true
                }
            }
        }
    }

    Dialog(
        onDismissRequest = {
            recordsViewModel.onEvent(RecordsEvent.CancelEditRecord)
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = true
        )
    ) {
        Box {
            if (isAlertDialogOpen.value) {
                AlertDialog(onDismissRequest = { isAlertDialogOpen.value = false },
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.extraLarge
                        )
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
                ) {
                    Column(horizontalAlignment = Alignment.End) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Amount can't be empty. Please enter some value")
                        }
                        TextButton(onClick = { isAlertDialogOpen.value = false }) {
                            Text(text = "Close", textAlign = TextAlign.End)
                        }
                    }
                }
            }
            Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }, topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Record detail")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            recordsViewModel.onEvent(RecordsEvent.CancelEditRecord)
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.close),
                                contentDescription = "Close icon"
                            )
                        }
                    }, actions = {
                        IconButton(onClick = {
                            isDeleteConfirmationOpen.value = true
                        }) {
                            Icon(painter = painterResource(id = R.drawable.delete), contentDescription = "Delete icon")
                        }
                        IconButton(onClick = {
                            editRecordViewModel.onEvent(EditRecordEvent.SaveRecord)
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.done),
                                contentDescription = "Save icon"
                            )
                        }
                    }
                )
            }) { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    TabRow(
                        selectedTabIndex = if (record.isExpenses) 1 else 0,
                        indicator = {},
                        divider = {},
                        modifier = Modifier
                            .padding(horizontal = 25.dp),
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        TabItem(
                            selected = !typeIsExpenses, onClick = {
                                editRecordViewModel.onEvent(
                                    EditRecordEvent.ChangeRecordType
                                )
                            }, text = "INCOME", modifier = Modifier.padding(end = 6.dp)
                        )
                        TabItem(
                            selected = typeIsExpenses, onClick = {
                                editRecordViewModel.onEvent(
                                    EditRecordEvent.ChangeRecordType
                                )
                            }, text = "EXPENSES", modifier = Modifier.padding(start = 6.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.63f)
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = if (typeIsExpenses) R.drawable.remove else R.drawable.add),
                            contentDescription = "Value indicator"
                        )

                        TextField(
                            value = amountState,
                            placeholder = { Text(text = "0") },
                            onValueChange = {
                                editRecordViewModel.onEvent(EditRecordEvent.EnteredAmount(it))
                            },
                            prefix = {
                                Text(text = if (typeIsExpenses) "-" else "+")
                            },
                            suffix = {
                                Text(text = "PHP")
                            })
                    }
                }
            }
        }
    }
}