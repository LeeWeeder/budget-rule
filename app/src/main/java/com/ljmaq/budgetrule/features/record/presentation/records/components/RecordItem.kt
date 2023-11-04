package com.ljmaq.budgetrule.features.record.presentation.records.components

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import com.ljmaq.budgetrule.features.record.domain.model.Record
import com.ljmaq.budgetrule.features.record.presentation.records.util.Formatter

@Composable
fun RecordItem(
    record: Record,
    isSelected: Boolean,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scale by animateFloatAsState(targetValue = if (isSelected) 1f else 0.2f, label = "scale", animationSpec = tween(durationMillis = 300, delayMillis = if (isSelected) 200 else 0, easing = CubicBezierEasing(0.16f, 1f, 0.3f, 1f)))
    ListItem(
        headlineContent = {
            Text(
                text = Formatter.formatCurrency(record.amount),
                color = if (record.amount.toDouble() < 0) {
                    Color(0xFFCC0202)
                } else {
                    Color(0xFF4BB543)
                }
            )
            Spacer(modifier = Modifier.height(4.dp))
        },
        supportingContent = {
            Box(
                modifier = Modifier
                    .background(
                        shape = MaterialTheme.shapes.small,
                        color = Color.Transparent
                    )
                    .clip(MaterialTheme.shapes.small)
                    .clickable {

                    }
                    .border(
                        width = 1.dp,
                        shape = MaterialTheme.shapes.small,
                        color = MaterialTheme.colorScheme.outline
                    )

                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(text = "Cash", style = MaterialTheme.typography.labelSmall)
            }
        },
        modifier = modifier,
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
                            modifier = Modifier.graphicsLayer(scaleY = scale, scaleX = scale)
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
            Text(text = Formatter.formatDate(record.timestamp))
        },
        tonalElevation = if (isSelected) 20.dp else 0.dp
    )
}