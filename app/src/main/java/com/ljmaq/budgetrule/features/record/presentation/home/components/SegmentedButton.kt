package com.ljmaq.budgetrule.features.record.presentation.home.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleChoiceSegmentedButtonRowScope.SingleChoiceSegmentedButton(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    position: SegmentedButtonValues = SegmentedButtonValues.MIDDLE,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    SegmentedButton(
        selected = selected, onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        shape = when (position) {
            SegmentedButtonValues.END -> EndSegmentedButtonShape
            SegmentedButtonValues.MIDDLE -> RectangleShape
            SegmentedButtonValues.START -> StartSegmentedButtonShape
        }
    ) {
        Text(text = text)
    }
}

private val StartSegmentedButtonShape =
    RoundedCornerShape(topStartPercent = 100, bottomStartPercent = 100)

private val EndSegmentedButtonShape =
    RoundedCornerShape(topEndPercent = 100, bottomEndPercent = 100)

sealed interface SegmentedButtonValues {
    data object START : SegmentedButtonValues
    data object END : SegmentedButtonValues
    data object MIDDLE : SegmentedButtonValues
}