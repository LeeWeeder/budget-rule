package com.ljmaq.budgetrule.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ljmaq.budgetrule.MainActivityViewModel
import com.ljmaq.budgetrule.R
import com.ljmaq.budgetrule.domain.model.Partition
import com.ljmaq.budgetrule.presentation.components.BudgetRuleAppBar
import com.ljmaq.budgetrule.presentation.components.ColorPicker
import com.ljmaq.budgetrule.presentation.components.DeletePartitionWarningDialog
import com.ljmaq.budgetrule.presentation.home.components.BudgetRuleButton
import com.ljmaq.budgetrule.presentation.home.components.BudgetRuleOutlinedButton
import com.ljmaq.budgetrule.presentation.home.components.BudgetRuleSheet
import com.ljmaq.budgetrule.presentation.home.components.DrawerSheetSubtitle
import com.ljmaq.budgetrule.presentation.home.components.PartitionItem
import com.ljmaq.budgetrule.presentation.home.components.SegmentedButtonValues
import com.ljmaq.budgetrule.presentation.home.components.SharePercentIndicator
import com.ljmaq.budgetrule.presentation.home.components.SingleChoiceSegmentedButton
import com.ljmaq.budgetrule.presentation.home.util.Formatter
import com.ljmaq.budgetrule.presentation.partition.PartitionViewModel
import com.ljmaq.budgetrule.util.Screen

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class
)
@Composable
fun HomeScreen(
    navController: NavController,
    onNavigateToOnBoardingScreen: () -> Unit,
    onNotShowOnBoarding: () -> Unit,
    fromOnBoarding: Boolean,
    viewModel: HomeViewModel = hiltViewModel(),
    partitionViewModel: PartitionViewModel = hiltViewModel(),
    createRecordViewModel: CreateRecordViewModel = hiltViewModel(),
    mainActivityViewModel: MainActivityViewModel = hiltViewModel()
) {
    val createRecordSheetVisibilityState = viewModel.createRecordSheetState.value
    val dialogState = viewModel.dialogState.value
    val partitionState = partitionViewModel.partitionState.value

    val balanceState = viewModel.balanceState.value
    val currencyCode = viewModel.currencyCode.value

    val excessPartitionState = partitionViewModel.excessPartitionState.collectAsState()

    var fabHeight by remember {
        mutableIntStateOf(0)
    }
    val fabHeightInDp = with(LocalDensity.current) {
        fabHeight.toDp()
    }

    if (!fromOnBoarding) {
        when (mainActivityViewModel.showOnBoarding.value) {
            true -> {
                LaunchedEffect(Unit) {
                    onNavigateToOnBoardingScreen()
                }
            }

            false -> {
                LaunchedEffect(Unit) {
                    onNotShowOnBoarding()
                }
            }

            else -> {
                Loading()
            }
        }
    }

// Disable for development purposes
//    val activity = LocalContext.current as? Activity
//
//    BackHandler {
//        activity?.finish()
//    }

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
                },
                modifier = Modifier.onGloballyPositioned {
                    fabHeight = it.size.height
                }
            )
        },
        topBar = {
            BudgetRuleAppBar(title = {
                Text(
                    text = "Budget Rule"
                )
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
            var partitionName by remember {
                mutableStateOf("")
            }
            var isError by remember {
                mutableStateOf(false)
            }
            var selectedColor by remember {
                mutableStateOf(Color(0xffab4568))
            }
            AlertDialog(onDismissRequest = {
                viewModel.onEvent(HomeEvent.HideNewPartitionDialog)
            }, confirmButton = {
                Button(onClick = {
                    isError = partitionName.isEmpty()
                    if (!isError) {
                        viewModel.onEvent(
                            HomeEvent.InsertPartition(
                                Partition(
                                    name = partitionName,
                                    amount = 0.0,
                                    sharePercent = 0f,
                                    color = selectedColor.toArgb().toLong() and 0x7fffffff,
                                    avatar = "😅"
                                )
                            )
                        )
                        navController.navigate(Screen.TuneSharePercentageScreen.route)
                        viewModel.onEvent(HomeEvent.HideNewPartitionDialog)
                    }
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Next")
                }
            }, text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Column {
                        var colorPickerVisible by remember {
                            mutableStateOf(false)
                        }
                        Text(
                            text = "Color",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                        Box(
                            modifier = Modifier.border(
                                width = TextFieldDefaults.FocusedIndicatorThickness,
                                shape = OutlinedTextFieldDefaults.shape,
                                color = OutlinedTextFieldDefaults.colors().unfocusedIndicatorColor
                            )
                        ) {
                            Button(
                                onClick = {
                                    colorPickerVisible = true
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = selectedColor),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp, horizontal = 6.dp),
                                shape = MaterialTheme.shapes.extraSmall
                            ) {

                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        AnimatedVisibility(
                            visible = colorPickerVisible
                        ) {
                            Popup(
                                properties = PopupProperties(focusable = true),
                                onDismissRequest = {
                                    colorPickerVisible = false
                                }
                            ) {
                                Surface(
                                    color = AlertDialogDefaults.containerColor,
                                    tonalElevation = AlertDialogDefaults.TonalElevation,
                                    shape = AlertDialogDefaults.shape
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .width(IntrinsicSize.Min)
                                            .height(IntrinsicSize.Min)
                                    ) {
                                        var newColor by remember {
                                            mutableStateOf(selectedColor)
                                        }
                                        ColorPicker(
                                            initialColor = newColor, onColorChanged = {
                                                if (it.fromUser) {
                                                    newColor = it.color
                                                }
                                            }
                                        )
                                        HorizontalDivider(
                                            color = MaterialTheme.colorScheme.outlineVariant,
                                            modifier = Modifier.padding(vertical = 12.dp)
                                        )
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End
                                        ) {
                                            TextButton(onClick = { colorPickerVisible = false }) {
                                                Text(text = "Cancel")
                                            }
                                            Spacer(modifier = Modifier.width(8.dp))
                                            TextButton(onClick = {
                                                selectedColor = newColor
                                                colorPickerVisible = false
                                            }) {
                                                Text(text = "Confirm")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    OutlinedTextField(value = partitionName, onValueChange = {
                        partitionName = it
                        isError = false
                    }, label = {
                        Text(text = "Partition name")
                    },
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
        DeletePartitionWarningDialog(
            isShowing = dialogState.deletePartitionWarningDialogShowing,
            onDismissRequest = {
                viewModel.onEvent(HomeEvent.HideDeletePartitionDialog)
            },
            onConfirm = {
                viewModel.onEvent(HomeEvent.DeletePartition(partition!!))
                viewModel.onEvent(HomeEvent.HideDeletePartitionDialog)
            }
        )
        val horizontalPadding = 10.dp
        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding)
                    .padding(top = 5.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                shape = CircleShape
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 2.dp, top = 14.dp, bottom = 16.dp)
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
                                isoCode = currencyCode
                            ),
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp)
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

            var longClickedItem by remember {
                mutableStateOf<Partition?>(null)
            }

            LazyColumn(
                contentPadding = PaddingValues(
                    bottom = paddingValues.calculateBottomPadding() + fabHeightInDp + 18.dp
                )
            ) {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = horizontalPadding)
                            .padding(bottom = 10.dp)
                    ) {
                        Card(
                            shape = CircleShape,
                            modifier = Modifier
                                .weight(1f),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(
                                    alpha = 0.3f
                                )
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .padding(start = 14.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = excessPartitionState.value.name,
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp)
                                    )
                                    Text(
                                        text = Formatter.formatCurrency(
                                            excessPartitionState.value.amount,
                                            isoCode = currencyCode
                                        ),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                SharePercentIndicator(sharePercent = excessPartitionState.value.sharePercent)
                            }
                        }
                        IconButton(
                            onClick = {
                                navController.navigate(Screen.TuneSharePercentageScreen.route)
                            },
                            colors = IconButtonDefaults.outlinedIconButtonColors(
                                contentColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.tune),
                                contentDescription = "Tune icon"
                            )
                        }
                        IconButton(
                            onClick = {
                                viewModel.onEvent(HomeEvent.ShowNewPartitionDialog)
                            },
                            colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                        ) {
                            Icon(
                                painterResource(id = R.drawable.add),
                                contentDescription = "Add icon"
                            )
                        }
                    }
                }

                items(
                    items = partitionState.partitionList,
                    key = {
                        it.id!!
                    }
                ) { partition ->
                    var isMenuExpanded by remember {
                        mutableStateOf(partition == longClickedItem)
                    }

                    LaunchedEffect(longClickedItem) {
                        isMenuExpanded = partition == longClickedItem
                    }

                    Box(modifier = Modifier.padding(horizontal = horizontalPadding)) {
                        var offset by remember {
                            mutableStateOf(Offset.Zero)
                        }
                        PartitionItem(
                            partition = partition, isMenuExpanded = isMenuExpanded,
                            viewModel = viewModel,
                            onDismissRequest = {
                                longClickedItem = null
                            },
                            currencyCode = currencyCode,
                            modifier = Modifier
                                .pointerInteropFilter {
                                    offset = Offset(x = it.x, y = it.y)
                                    println(offset.x)
                                    println(offset.y)
                                    false
                                }
                                .combinedClickable(
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    },
                                    indication = rememberRipple(color = Color(partition.color!!)),
                                    onLongClick = {
                                        longClickedItem = partition
                                    },
                                    onClick = {

                                    },
                                    enabled = longClickedItem == null || longClickedItem == partition
                                ),
                            offset = offset
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }
}

@Composable
fun Loading() {
    Text(text = "Loading")
}