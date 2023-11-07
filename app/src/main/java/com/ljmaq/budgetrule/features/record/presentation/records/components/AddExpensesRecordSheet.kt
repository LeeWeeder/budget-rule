package com.ljmaq.budgetrule.features.record.presentation.records.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ljmaq.budgetrule.R
import com.ljmaq.budgetrule.features.record.domain.model.Category
import com.ljmaq.budgetrule.features.record.presentation.records.util.Formatter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddExpensesRecordSheet(
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    category: Category,
    paddingValues: PaddingValues
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = Modifier
            .consumeWindowInsets(paddingValues)
            .padding(paddingValues),
        dragHandle = {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                            MaterialTheme.shapes.extraLarge
                        )
                        .width(40.dp)
                        .height(3.5.dp)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                MediumTopAppBar(title = {
                    Text(
                        text = Formatter.formatCurrency(category.amount.toString()),
                        color = category.contentColor
                    )
                }, navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.close),
                            contentDescription = "Close icon"
                        )
                    }
                }, actions = {
                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .height(36.dp)
                    ) {
                        Button(onClick = {  }, contentPadding = PaddingValues(4.dp)) {
                            Text(text = "Save")
                        }
                    }
                })
            }
        ) {
            LazyColumn(contentPadding = it) {
                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Divider()
                        val amountTextStyle = MaterialTheme.typography.headlineMedium
                        val focusRequester = FocusRequester()
                        TextField(value = "",
                            onValueChange = {},
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            leadingIcon = {},
                            placeholder = {
                                Text(text = "Amount", style = amountTextStyle)
                            },
                            textStyle = amountTextStyle,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal,
                                imeAction = ImeAction.Done
                            ),
                            modifier = Modifier.focusRequester(focusRequester)
                        )
                        LaunchedEffect(Unit) {
                            focusRequester.requestFocus()
                        }
                    }
                }
            }
        }
    }
}