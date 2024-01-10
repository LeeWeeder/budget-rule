package com.ljmaq.budgetrule.presentation.tune_share_percentage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ljmaq.budgetrule.R
import com.ljmaq.budgetrule.domain.model.Partition
import com.ljmaq.budgetrule.presentation.components.BudgetRuleAppBar
import com.ljmaq.budgetrule.presentation.partition.PartitionViewModel
import com.ljmaq.budgetrule.presentation.tune_share_percentage.components.PartitionItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TuneSharePercentageScreen(
    navController: NavController,
    viewModel: TuneSharePercentageViewModel = hiltViewModel(),
    partitionViewModel: PartitionViewModel = hiltViewModel()
) {
    val partitionState = partitionViewModel.partitionState.value
    val leftOverPartitionState = partitionViewModel.excessPartitionState.collectAsState()

    val sharePercentageList = viewModel.partitions.value
    val leftOverPartitionSharePercent = viewModel.leftOverPartitionState.value

    LaunchedEffect(partitionState.partitionList.isNotEmpty()) {
        viewModel.onEvent(
            TuneSharePercentageEvent.Initialize(
                partitionState.partitionList,
                leftOverPartitionState.value
            )
        )
    }

    Scaffold(
        topBar = {
            BudgetRuleAppBar(title = {
                Text(text = "Tune Partition")
            }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Arrow back icon"
                    )
                }
            }, actions = {
                Button(
                    onClick = {
                        viewModel.onEvent(TuneSharePercentageEvent.SaveTuneSharePercentage)
                        navController.popBackStack()
                    }, modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(text = "Save")
                }
            }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(paddingValues)
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PartitionItem(
                partition = leftOverPartitionSharePercent,
                onValueChange = {
                    val leftOverPartition = leftOverPartitionState.value
                    viewModel.onEvent(
                        TuneSharePercentageEvent.EditLeftOverSharePercent(
                            Partition(
                                id = leftOverPartition.id,
                                amount = leftOverPartition.amount,
                                name = leftOverPartition.name,
                                sharePercent = it
                            )
                        )
                    )
                },
                isLeftOverPartition = true,
                modifier = Modifier.padding(horizontal = 16.dp)

            )
            HorizontalDivider()
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = sharePercentageList.partitions
                ) { partition ->
                    sharePercentageList.partitions.find { sharePercentage ->
                        sharePercentage.id == partition.id
                    }?.let { partition1 ->
                        PartitionItem(partition = partition, onValueChange = {
                            viewModel.onEvent(
                                TuneSharePercentageEvent.EditSharePercent(
                                    Partition(
                                        id = partition1.id,
                                        name = partition1.name,
                                        amount = partition1.amount,
                                        sharePercent = it
                                    )
                                )
                            )
                        })
                    }
                }
            }
        }
    }
}