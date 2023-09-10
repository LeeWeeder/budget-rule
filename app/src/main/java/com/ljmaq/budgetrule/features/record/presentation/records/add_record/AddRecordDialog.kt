package com.ljmaq.budgetrule.features.record.presentation.records.add_record

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.ljmaq.budgetrule.features.record.presentation.records.RecordsEvent
import com.ljmaq.budgetrule.features.record.presentation.records.RecordsViewModel
import com.ljmaq.budgetrule.features.record.presentation.records.add_record.components.TabItem
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddRecordDialog(
    viewModel: AddRecordViewModel = hiltViewModel(),
    recordsViewModel: RecordsViewModel = hiltViewModel()
) {
    val typeIsExpenses = viewModel.typeIsExpenses.value
    val amountState = viewModel.recordAmount.value
    val tabState = viewModel.tabState.value

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddRecordViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is AddRecordViewModel.UiEvent.SaveRecord -> {
                    recordsViewModel.onEvent(RecordsEvent.CreateRecord)
                }
            }
        }
    }

    Dialog(
        onDismissRequest = {
            recordsViewModel.onEvent(RecordsEvent.CancelCreateRecord)
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = true
        )
    ) {
        Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }, topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    recordsViewModel.onEvent(RecordsEvent.CancelCreateRecord)
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close icon"
                    )
                }
                IconButton(onClick = {
                    viewModel.onEvent(AddRecordEvent.SaveRecord)
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Save icon"
                    )
                }
            }
        }) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                TabRow(selectedTabIndex = tabState.index, indicator = { tabPositions ->
                    Box(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[tabState.index])
                            .padding(horizontal = 60.dp)
                            .height(4.dp)
                            .background(
                                MaterialTheme.colorScheme.primary,
                                shape = MaterialTheme.shapes.extraLarge
                            )
                    )
                }, divider = {}, modifier = Modifier.padding(horizontal = 20.dp)) {
                    TabItem(
                        selected = !typeIsExpenses, onClick = {
                            viewModel.onEvent(
                                AddRecordEvent.ChangeRecordType
                            )
                        }, text = "INCOME"
                    )

                    TabItem(
                        selected = typeIsExpenses, onClick = {
                            viewModel.onEvent(
                                AddRecordEvent.ChangeRecordType
                            )
                        }, text = "EXPENSES"
                    )
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        value = amountState.value,
                        placeholder = { Text(text = "0") },
                        onValueChange = {
                            viewModel.onEvent(AddRecordEvent.EnteredAmount(it))
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