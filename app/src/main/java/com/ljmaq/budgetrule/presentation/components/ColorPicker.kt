package com.ljmaq.budgetrule.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun ColorPicker(
    controller: ColorPickerController = rememberColorPickerController(),
    onColorChanged: (ColorEnvelope) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(200.dp)) {
        HsvColorPicker(
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 24.dp), controller = controller,
            onColorChanged = onColorChanged
        )
        BrightnessSlider(controller = controller, modifier = Modifier.height(25.dp))
    }
}