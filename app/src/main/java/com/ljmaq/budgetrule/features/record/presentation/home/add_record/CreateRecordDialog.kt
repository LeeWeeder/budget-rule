//package com.ljmaq.budgetrule.features.record.presentation.home.add_record
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Check
//import androidx.compose.material3.AlertDialog
//import androidx.compose.material3.Divider
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.ModalBottomSheet
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.SnackbarHost
//import androidx.compose.material3.SnackbarHostState
//import androidx.compose.material3.TabRow
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.drawWithContent
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.window.Dialog
//import androidx.compose.ui.window.DialogProperties
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.ljmaq.budgetrule.R
//import com.ljmaq.budgetrule.features.record.presentation.home.HomeEvent
//import com.ljmaq.budgetrule.features.record.presentation.home.HomeViewModel
//import com.ljmaq.budgetrule.features.record.presentation.home.add_record.components.ButtonRow
//import com.ljmaq.budgetrule.features.record.presentation.home.add_record.components.KeyButton
//import com.ljmaq.budgetrule.features.record.presentation.home.add_record.components.TabItem
//import com.ljmaq.budgetrule.features.record.presentation.home.util.Formatter.Companion.formatNumber
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.flow.collectLatest
//import kotlinx.coroutines.launch
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CreateRecordDialog(
//    viewModel: AddRecordViewModel = hiltViewModel(),
//    recordsViewModel: HomeViewModel = hiltViewModel(),
//    snackbarHostState: SnackbarHostState,
//    scope: CoroutineScope
//) {
//    val amountState = viewModel.amount.value
//    val thisSnackbarHostState = remember {
//        SnackbarHostState()
//    }
//
//    LaunchedEffect(key1 = true) {
//        viewModel.eventFlow.collectLatest { event ->
//            when (event) {
//                is AddRecordViewModel.UiEvent.ShowSnackbar -> {
//                    thisSnackbarHostState.showSnackbar(event.message)
//                }
//
//                is AddRecordViewModel.UiEvent.SaveRecord -> {
//                    recordsViewModel.onEvent(HomeEvent.CreateRecord)
//                    scope.launch {
//                        snackbarHostState.showSnackbar(
//                            message = "Record saved",
//                            withDismissAction = true
//                        )
//                    }
//                }
//
//                is AddRecordViewModel.UiEvent.ShowAmountWarningDialog -> {
//
//                }
//            }
//        }
//    }
//
//    ModalBottomSheet(onDismissRequest = { /*TODO*/ }, modifier = Modifier.padding(16.dp)) {
//        Column(verticalArrangement = Arrangement.Center) {
//            Icon(imageVector = Icons.Default.Check, contentDescription = null)
//            Text(text = "Configure record", style = MaterialTheme.typography.titleLarge)
//            Text(text = "Adjust this record", style = MaterialTheme.typography.bodySmall)
//        }
//        Column {
//            Text(text = "Record type", style = MaterialTheme.typography.labelMedium)
//
//        }
//    }
//
//    Dialog(
//        onDismissRequest = {
//            recordsViewModel.onEvent(HomeEvent.CancelCreateRecord)
//        },
//        properties = DialogProperties(
//            usePlatformDefaultWidth = false,
//            decorFitsSystemWindows = true
//        )
//    ) {
//        Box {
//            if (isAlertDialogOpen.value) {
//                AlertDialog(onDismissRequest = { isAlertDialogOpen.value = false },
//                    modifier = Modifier
//                        .background(
//                            color = MaterialTheme.colorScheme.surface,
//                            shape = MaterialTheme.shapes.extraLarge
//                        )
//                        .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
//                ) {
//                    Column(horizontalAlignment = Alignment.End) {
//                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                            Text(text = "Amount can't be empty. Please enter some value")
//                        }
//                        TextButton(onClick = { isAlertDialogOpen.value = false }) {
//                            Text(text = "Close", textAlign = TextAlign.End)
//                        }
//                    }
//                }
//            }
//            Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }, topBar = {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 12.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    IconButton(onClick = {
//                        recordsViewModel.onEvent(HomeEvent.CancelCreateRecord)
//                    }) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.close),
//                            contentDescription = "Close icon"
//                        )
//                    }
//                    IconButton(onClick = {
//                        viewModel.onEvent(AddRecordEvent.SaveRecord)
//                    }) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.done),
//                            contentDescription = "Save icon"
//                        )
//                    }
//                }
//            }, containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary) { paddingValues ->
//                Column(
//                    modifier = Modifier
//                        .padding(paddingValues)
//                        .fillMaxSize()
//                ) {
//                    Spacer(modifier = Modifier.height(16.dp))
//                    TabRow(
//                        selectedTabIndex = tabState.index,
//                        indicator = {},
//                        divider = {},
//                        modifier = Modifier
//                            .padding(horizontal = 25.dp),
//                        containerColor = MaterialTheme.colorScheme.primary
//                    ) {
//                        TabItem(
//                            selected = !typeIsExpenses, onClick = {
//                                viewModel.onEvent(
//                                    AddRecordEvent.ChangeRecordType(0)
//                                )
//                            }, text = "INCOME", modifier = Modifier.padding(end = 6.dp)
//                        )
//                        TabItem(
//                            selected = typeIsExpenses, onClick = {
//                                viewModel.onEvent(
//                                    AddRecordEvent.ChangeRecordType(1)
//                                )
//                            }, text = "EXPENSES", modifier = Modifier.padding(start = 6.dp)
//                        )
//                    }
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .fillMaxHeight(0.63f)
//                            .padding(horizontal = 12.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Icon(
//                            painter = painterResource(id = if (typeIsExpenses) R.drawable.remove else R.drawable.add),
//                            contentDescription = "Value indicator"
//                        )
//                        var readyToDraw by remember {
//                            mutableStateOf(false)
//                        }
//                        Text(
//                            text = formatNumber(amountState.value),
//                            style = MaterialTheme.typography.displayLarge.copy(fontSize = amountFontSizeState.value.sp),
//                            modifier = Modifier
//                                .fillMaxWidth(0.8f)
//                                .drawWithContent {
//                                    if (readyToDraw) {
//                                        drawContent()
//                                    }
//                                },
//                            maxLines = 1,
//                            textAlign = TextAlign.End,
//                            overflow = TextOverflow.Clip,
//                            onTextLayout = {
//                                if (it.didOverflowWidth) {
//                                    viewModel.onEvent(AddRecordEvent.OverflowAmountTextField)
//                                } else {
//                                    readyToDraw = true
//                                }
//                            },
//                            softWrap = false
//                        )
//                        Text(
//                            text = "PHP",
//                            style = MaterialTheme.typography.headlineLarge,
//                            modifier = Modifier.padding(start = 8.dp)
//                        )
//                    }
//                    Spacer(modifier = Modifier.height(1.dp))
//                    Divider(thickness = 1.dp)
//                    Row(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(MaterialTheme.colorScheme.surface), horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Column(
//                            verticalArrangement = Arrangement.SpaceBetween,
//                            modifier = Modifier
//                                .fillMaxHeight()
//                                .padding(vertical = 12.dp)
//                        ) {
//                            ButtonRow {
//                                KeyButton(number = "7") {
//                                    viewModel.onEvent(AddRecordEvent.EnteredAmount("7"))
//                                }
//                                KeyButton(number = "8") {
//                                    viewModel.onEvent(AddRecordEvent.EnteredAmount("8"))
//                                }
//                                KeyButton(number = "9") {
//                                    viewModel.onEvent(AddRecordEvent.EnteredAmount("9"))
//                                }
//                            }
//                            ButtonRow {
//                                KeyButton(number = "4") {
//                                    viewModel.onEvent(AddRecordEvent.EnteredAmount("4"))
//                                }
//                                KeyButton(number = "5") {
//                                    viewModel.onEvent(AddRecordEvent.EnteredAmount("5"))
//                                }
//                                KeyButton(number = "6") {
//                                    viewModel.onEvent(AddRecordEvent.EnteredAmount("6"))
//                                }
//                            }
//                            ButtonRow {
//                                KeyButton(number = "1") {
//                                    viewModel.onEvent(AddRecordEvent.EnteredAmount("1"))
//                                }
//                                KeyButton(number = "2") {
//                                    viewModel.onEvent(AddRecordEvent.EnteredAmount("2"))
//                                }
//                                KeyButton(number = "3") {
//                                    viewModel.onEvent(AddRecordEvent.EnteredAmount("3"))
//                                }
//                            }
//                            ButtonRow {
//                                KeyButton {
//                                    viewModel.onEvent(AddRecordEvent.EnteredDecimal)
//                                }
//                                KeyButton(number = "0") {
//                                    viewModel.onEvent(AddRecordEvent.EnteredAmount("0"))
//                                }
//                                KeyButton(
//                                    icon = painterResource(id = R.drawable.backspace),
//                                    contentDescription = "Backspace icon"
//                                ) {
//                                    viewModel.onEvent(AddRecordEvent.BackSpace)
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}