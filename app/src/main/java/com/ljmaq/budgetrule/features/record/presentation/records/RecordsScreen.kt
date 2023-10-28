package com.ljmaq.budgetrule.features.record.presentation.records

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.ljmaq.budgetrule.features.record.presentation.records.components.RecordItem
import com.ljmaq.budgetrule.features.record.presentation.util.Screen
import kotlinx.coroutines.launch

@Composable
fun RecordScreen(
    navController: NavController,
    viewModel: RecordsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val categoryState = viewModel.categoryState.value
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    val dialogState = viewModel.isDialogShowing.value

    Box {
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
                GreetingsAppBar()
            }) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
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
                Text(text = "Records", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.records) { record ->
                        RecordItem(record = record, modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.EditRecordScreen.route + "?recordId=${record.id}"
                                )
                            }, onDeleteClick = {
                            viewModel.onEvent(RecordsEvent.DeleteRecord(record))
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Record deleted",
                                    actionLabel = "Undo"
                                )

                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(RecordsEvent.RestoreRecord)
                                }
                            }
                        })
                    }
                }
            }
        }
        if (dialogState) AddRecordDialog(snackbarHostState = snackbarHostState, scope = scope)
    }
}