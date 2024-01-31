package com.ljmaq.budgetrule.presentation.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.ljmaq.budgetrule.R
import com.ljmaq.budgetrule.domain.model.Partition
import com.ljmaq.budgetrule.presentation.home.HomeEvent
import com.ljmaq.budgetrule.presentation.home.HomeViewModel
import com.ljmaq.budgetrule.presentation.home.util.Formatter
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.PartitionItem(
    partition: Partition,
    offset: Offset,
    isMenuExpanded: Boolean,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    currencyCode: String,
    onDismissRequest: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.animateItemPlacement()
    ) {
        Column(modifier = modifier) {
            PartitionItemContent(
                partition = partition,
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp)
                ), currencyCode = currencyCode
            )
            AnimatedVisibility(isMenuExpanded) {
                Popup(
                    alignment = Alignment.BottomCenter,
                    onDismissRequest = onDismissRequest,
                    offset = IntOffset(
                        x = offset.x.roundToInt() + 100,
                        y = offset.y.roundToInt() + 50
                    ),
                    properties = PopupProperties(focusable = true)
                ) {
                    ElevatedCard(
                        modifier = Modifier
                            .width(150.dp),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
                        shape = MaterialTheme.shapes.extraSmall
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        DropdownMenuItem(
                            text = { Text(text = "Edit") },
                            onClick = {
                                viewModel.onEvent(HomeEvent.EditPartitionMenuItemClick(partition))
                                onDismissRequest()
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.edit),
                                    contentDescription = "Edit icon"
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Delete") },
                            onClick = {
                                viewModel.onEvent(HomeEvent.DeletePartitionMenuItemClick(partition))
                                onDismissRequest()
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.delete),
                                    contentDescription = "Delete icon"
                                )
                            },
                            colors = MenuDefaults.itemColors(
                                textColor = MaterialTheme.colorScheme.error,
                                leadingIconColor = MaterialTheme.colorScheme.error
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun PartitionItemContent(
    partition: Partition,
    colors: ListItemColors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
    elevation: Dp = 12.dp,
    currencyCode: String
) {
    ListItem(
        headlineContent = {
            Text(
                text = Formatter.formatCurrency(partition.amount, currencyCode),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Right
            )
        },
        overlineContent = {
            Text(text = partition.name)
        },
        trailingContent = {
            SharePercentIndicator(partition.sharePercent)
        },
        colors = colors,
        shadowElevation = elevation,
        tonalElevation = elevation
    )
}

@Composable
fun SharePercentIndicator(sharePercent: Float) {
    Box(contentAlignment = Alignment.Center) {
        Text(
            text = "${(sharePercent * 100).toInt()}%",
            style = MaterialTheme.typography.labelSmall
        )
        CircularProgressIndicator(
            progress = sharePercent,
            trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            strokeCap = StrokeCap.Round
        )
    }
}
