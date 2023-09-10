package com.ljmaq.budgetrule.features.record.presentation.edit_record

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditRecordScreen(
    navController: NavController,
    viewModel: EditRecordViewModel = hiltViewModel()
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

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }, topBar = {
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                IconButton(onClick = {
                    viewModel.onEvent(EditRecordEvent.SaveRecord)
                    navController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Save icon"
                    )
                }
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close icon"
                    )
                }
            }
            TabRow(selectedTabIndex = tabState.index) {
                Tab(
                    selected = !typeIsExpenses, onClick = {
                        viewModel.onEvent(
                            EditRecordEvent.ChangeRecordType
                        )
                    }) {
                    Text(text = "Income")
                }
                Tab(selected = typeIsExpenses, onClick = {
                    viewModel.onEvent(
                        EditRecordEvent.ChangeRecordType
                    )
                }) {
                    Text(text = "Expenses")
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = amountState.value,
                    placeholder = { Text(text = "0") },
                    onValueChange = {
                        viewModel.onEvent(EditRecordEvent.EnteredAmount(it))
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