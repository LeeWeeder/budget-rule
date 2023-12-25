package com.ljmaq.budgetrule.features.budget_rule.presentation.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.ljmaq.budgetrule.features.budget_rule.domain.model.Partition
import com.ljmaq.budgetrule.features.budget_rule.presentation.home.HomeEvent
import com.ljmaq.budgetrule.features.budget_rule.presentation.home.HomeViewModel
import com.ljmaq.budgetrule.features.budget_rule.presentation.home.util.Formatter
import kotlin.math.roundToInt

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun PartitionItem(
    partition: Partition,
    modifier: Modifier = Modifier,
    isMenuExpanded: Boolean,
    viewModel: HomeViewModel,
    onDismissRequest: () -> Unit,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    var offset by remember {
        mutableStateOf(Offset.Zero)
    }
    ElevatedCard(
        modifier = modifier
            .clip(CardDefaults.elevatedShape)
            .pointerInteropFilter {
                offset = Offset(x = it.x, y = it.y)
                println(offset.x)
                println(offset.y)
                false
            }
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
    ) {
        PartitionItemContent(partition = partition)
        if (isMenuExpanded) {
            Popup(
                onDismissRequest = onDismissRequest,
                offset = IntOffset(x = offset.x.roundToInt(), y = offset.y.roundToInt())
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .width(112.dp)
                        .padding(vertical = 8.dp)
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "Edit") },
                        onClick = {
                            viewModel.onEvent(HomeEvent.EditPartitionMenuItemClick(partition))
                            onDismissRequest()
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Delete") },
                        onClick = {
                            viewModel.onEvent(HomeEvent.DeletePartitionMenuItemClick(partition))
                            onDismissRequest()
                        })
                }
            }
        }
    }
}

@Composable
private fun PartitionItemContent(partition: Partition) {
    val padding = 16.dp
    Row(
        modifier = Modifier
            .padding(padding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = partition.name, style = MaterialTheme.typography.labelLarge)
            Text(
                text = Formatter.formatCurrency(partition.amount.toString(), "PHP"),
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Right
            )
        }
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = "${(partition.sharePercent * 100).toInt()}%",
                style = MaterialTheme.typography.labelSmall
            )
            CircularProgressIndicator(
                progress = partition.sharePercent
            )
        }
    }
}
