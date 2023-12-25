package com.ljmaq.budgetrule.features.budget_rule.presentation.home.components

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ljmaq.budgetrule.R
import com.ljmaq.budgetrule.features.budget_rule.domain.model.Income

@Composable
fun IncomeItem(
    income: Income,
    isSelected: Boolean,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // TODO: Add ripple effect on icon click during highlighting of item
    val roundCorner = 18.dp
    val roundedStartCornerShape =
        RoundedCornerShape(topStart = roundCorner, bottomStart = roundCorner)

    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0.2f,
        label = "scale",
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = if (isSelected) 200 else 0,
            easing = CubicBezierEasing(0.16f, 1f, 0.3f, 1f)
        )
    )
    Box(
        modifier = Modifier
            .padding(start = 6.dp)
            .clip(roundedStartCornerShape)
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = "Formatter.formatCurrency(income.amount, PHP)",
                    color = Color(0xFF4BB543)
                )
            },
            supportingContent = {
                Row {
                    /*partitions.onEach { partition ->
                        PartitionChip(onClick = { *//*TODO*//* }, partition = partition)
                 }*/
                }
            },
            modifier = modifier.clip(roundedStartCornerShape),
            leadingContent = {
                RecordItemIcon(
                    cardFace = if (isSelected) RecordItemIconFace.Front else RecordItemIconFace.Back,
                    front = {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                )
                                .size(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.check),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                contentDescription = "Selected icon",
                                modifier = Modifier.graphicsLayer(
                                    scaleY = scale,
                                    scaleX = scale
                                )
                            )
                        }
                    },
                    back = {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = CircleShape
                                )
                                .size(40.dp)
                                .graphicsLayer {
                                    rotationY = 180f
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.attach_money),
                                contentDescription = "Cash icon",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    },
                    onClick = onIconClick
                )
            },
            trailingContent = {
                Text(text = "Formatter.formatDate(income.timestamp)")
            },
            tonalElevation = if (isSelected) 20.dp else 0.dp
        )
    }
}