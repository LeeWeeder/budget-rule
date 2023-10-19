package com.ljmaq.budgetrule.features.record.presentation.records.add_record

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.ljmaq.budgetrule.features.record.presentation.records.RecordsEvent
import com.ljmaq.budgetrule.features.record.presentation.records.RecordsViewModel
import com.ljmaq.budgetrule.features.record.presentation.records.add_record.components.ButtonRow
import com.ljmaq.budgetrule.features.record.presentation.records.add_record.components.KeyButton
import com.ljmaq.budgetrule.features.record.presentation.records.add_record.components.TabItem
import com.ljmaq.budgetrule.features.record.presentation.records.util.Formatter.Companion.formatNumber
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddRecordDialog(
    viewModel: AddRecordViewModel = hiltViewModel(),
    recordsViewModel: RecordsViewModel = hiltViewModel()
) {
    val typeIsExpenses = viewModel.typeIsExpenses.value
    val amountState = viewModel.recordAmount.value
    val tabState = viewModel.tabState.value
    val amountFontSizeState = viewModel.amountTextFieldFontSizeState.value

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
                Spacer(modifier = Modifier.height(16.dp))
                TabRow(
                    selectedTabIndex = tabState.index,
                    indicator = {},
                    divider = {},
                    modifier = Modifier
                        .padding(horizontal = 25.dp)
                        .border(
                            BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                            shape = MaterialTheme.shapes.extraLarge
                        )
                ) {
                    TabItem(
                        selected = !typeIsExpenses, onClick = {
                            viewModel.onEvent(
                                AddRecordEvent.ChangeRecordType(0)
                            )
                        }, text = "INCOME"
                    )
                    TabItem(
                        selected = typeIsExpenses, onClick = {
                            viewModel.onEvent(
                                AddRecordEvent.ChangeRecordType(1)
                            )
                        }, text = "EXPENSES"
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.55f)
                        .padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (typeIsExpenses) Icons.Default.Remove else Icons.Default.Add,
                        contentDescription = "Value indicator"
                    )
                    var readyToDraw by remember {
                        mutableStateOf(false)
                    }
                    Text(
                        text = formatNumber(amountState.value),
                        style = MaterialTheme.typography.displayLarge.copy(fontSize = amountFontSizeState.value.sp),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .drawWithContent {
                                if (readyToDraw) {
                                    drawContent()
                                }
                            },
                        maxLines = 1,
                        textAlign = TextAlign.End,
                        overflow = TextOverflow.Clip,
                        onTextLayout = {
                            if (it.didOverflowWidth) {
                                viewModel.onEvent(AddRecordEvent.OverflowAmountTextField)
                            } else {
                                readyToDraw = true
                            }
                        },
                        softWrap = false
                    )
                    Text(
                        text = "PHP",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Divider(thickness = 2.dp)
                Spacer(modifier = Modifier.height(1.dp))
                Divider(thickness = 1.dp)
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        ButtonRow {
                            KeyButton(number = "7") {
                                viewModel.onEvent(AddRecordEvent.EnteredAmount("7"))
                            }
                            KeyButton(number = "8") {
                                viewModel.onEvent(AddRecordEvent.EnteredAmount("8"))
                            }
                            KeyButton(number = "9") {
                                viewModel.onEvent(AddRecordEvent.EnteredAmount("9"))
                            }
                        }
                        ButtonRow {
                            KeyButton(number = "4") {
                                viewModel.onEvent(AddRecordEvent.EnteredAmount("4"))
                            }
                            KeyButton(number = "5") {
                                viewModel.onEvent(AddRecordEvent.EnteredAmount("5"))
                            }
                            KeyButton(number = "6") {
                                viewModel.onEvent(AddRecordEvent.EnteredAmount("6"))
                            }
                        }
                        ButtonRow {
                            KeyButton(number = "1") {
                                viewModel.onEvent(AddRecordEvent.EnteredAmount("1"))
                            }
                            KeyButton(number = "2") {
                                viewModel.onEvent(AddRecordEvent.EnteredAmount("2"))
                            }
                            KeyButton(number = "3") {
                                viewModel.onEvent(AddRecordEvent.EnteredAmount("3"))
                            }
                        }
                        ButtonRow {
                            KeyButton {
                                viewModel.onEvent(AddRecordEvent.EnteredDecimal)
                            }
                            KeyButton(number = "0") {
                                viewModel.onEvent(AddRecordEvent.EnteredAmount("0"))
                            }
                            KeyButton(
                                icon = Icons.Default.Backspace,
                                contentDescription = "Backspace icon"
                            ) {
                                viewModel.onEvent(AddRecordEvent.BackSpace)
                            }
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        KeyButton(operation = '/') {
                            viewModel.onEvent(AddRecordEvent.EnteredOperation('/'))
                        }
                        KeyButton(operation = '*') {
                            viewModel.onEvent(AddRecordEvent.EnteredOperation('*'))
                        }
                        KeyButton(operation = '-') {
                            viewModel.onEvent(AddRecordEvent.EnteredOperation('-'))
                        }
                        KeyButton(operation = '+') {
                            viewModel.onEvent(AddRecordEvent.EnteredOperation('+'))
                        }
                        KeyButton(operation = '=') {
                            viewModel.onEvent(AddRecordEvent.Equals)
                        }
                    }
                }
            }
        }
    }
}