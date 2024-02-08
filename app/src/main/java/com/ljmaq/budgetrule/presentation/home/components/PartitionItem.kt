package com.ljmaq.budgetrule.presentation.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInElastic
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.ljmaq.budgetrule.R
import com.ljmaq.budgetrule.domain.model.Partition
import com.ljmaq.budgetrule.presentation.home.HomeEvent
import com.ljmaq.budgetrule.presentation.home.HomeViewModel
import com.ljmaq.budgetrule.presentation.home.util.Formatter
import com.ljmaq.budgetrule.util.dpToSp
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
        modifier = Modifier.animateItemPlacement(),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Column(modifier = modifier) {
            PartitionItemContent(
                partition = partition,
                currencyCode = currencyCode,
                isMenuExpanded = isMenuExpanded
            )
            AnimatedVisibility(
                visible = isMenuExpanded,
                enter = scaleIn(
                    animationSpec = tween(durationMillis = 5000, easing = EaseInElastic),
                    initialScale = 0f,
                    transformOrigin = TransformOrigin(0f, 0f)
                ),
                exit = scaleOut(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessHigh
                    )
                )
            ) {
                Popup(
                    alignment = Alignment.BottomCenter,
                    onDismissRequest = onDismissRequest,
                    offset = IntOffset(
                        x = offset.x.roundToInt(),
                        y = offset.y.roundToInt()
                    ),
                    properties = PopupProperties(focusable = true)
                ) {
                    ElevatedCard(
                        modifier = Modifier
                            .width(IntrinsicSize.Min),
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
    currencyCode: String,
    isMenuExpanded: Boolean
) {
    val color = Color(partition.color!!)

    ListItem(
        headlineContent = {
            Text(
                text = Formatter.formatCurrency(partition.amount, currencyCode),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Right,
                fontWeight = FontWeight.Bold
            )
        },
        overlineContent = {
            Text(text = partition.name, fontSize = 10.sp)
        },
        trailingContent = {
            SharePercentIndicator(
                partition.sharePercent,
                trackColor = color.copy(alpha = 0.15f),
                color = color
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = if (isMenuExpanded) {
                color.copy(alpha = 0.15f)
            } else {
                ListItemDefaults.containerColor
            },
            overlineColor = MaterialTheme.colorScheme.secondary
        ),
        leadingContent = {
            val textSizeDp = 20.dp
            val textSizeSp = dpToSp(value = textSizeDp)
            Box(
                modifier = Modifier
                    .background(
                        shape = CircleShape,
                        color = color.copy(alpha = 0.1f)
                    )
                    .size(46.dp),
                contentAlignment = Alignment.Center
            ) {
                partition.avatar?.let {
                    Text(
                        text = it,
                        fontSize = textSizeSp
                    )
                }
            }
        }
    )
}

@Composable
fun SharePercentIndicator(
    sharePercent: Float,
    trackColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
    color: Color = MaterialTheme.colorScheme.primary
) {
    val textSizeDp = 10.dp
    val textSizeSp = dpToSp(value = textSizeDp)
    Box(contentAlignment = Alignment.Center) {
        Text(
            text = "${(sharePercent * 100).toInt()}%",
            style = MaterialTheme.typography.labelSmall.merge(fontSize = textSizeSp)
        )
        CircularProgressIndicator(
            progress = { sharePercent },
            color = color,
            trackColor = trackColor,
            strokeCap = StrokeCap.Round,
        )
    }
}
