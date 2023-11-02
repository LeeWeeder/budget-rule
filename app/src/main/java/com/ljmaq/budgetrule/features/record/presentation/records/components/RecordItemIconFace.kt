package com.ljmaq.budgetrule.features.record.presentation.records.components

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

enum class RecordItemIconFace(val angle: Float) {
    Front(0f),
    Back(180f)
}

@Composable
fun RecordItemIcon(
    cardFace: RecordItemIconFace,
    back: @Composable () -> Unit = {},
    front: @Composable () -> Unit = {},
) {
    val rotation = animateFloatAsState(
        targetValue = cardFace.angle,
        animationSpec = tween(
            durationMillis = 300,
            easing = EaseInOutCubic
        ), label = "icon flip"
    )
    Box(modifier = Modifier.graphicsLayer {
        rotationY = rotation.value
        cameraDistance = 12f * density
    },
        contentAlignment = Alignment.Center
    ) {
        if (rotation.value <= 90f) {
            front()
        } else {
            back()
        }
    }
}