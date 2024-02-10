package com.ljmaq.budgetrule.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.ljmaq.budgetrule.R
import com.ljmaq.budgetrule.util.toImageBitmap

@Composable
fun ColorPicker(
    initialColor: Color,
    modifier: Modifier = Modifier,
    controller: ColorPickerController = rememberColorPickerController(),
    onColorChanged: (ColorEnvelope) -> Unit
) {
    Row(modifier = Modifier.padding(start = 8.dp)) {
        var height by remember {
            mutableStateOf(0.dp)
        }
        val density = LocalDensity.current
        AlphaTile(
            modifier = Modifier
                .height(height)
                .width(36.dp),
            controller = controller
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .width(200.dp)
                .onGloballyPositioned {
                    height = with(density) {
                        it.size.height.toDp()
                    }
                }
        ) {
            val context = LocalContext.current
            HsvColorPicker(
                initialColor = initialColor,
                modifier = Modifier
                    .size(180.dp)
                    .padding(bottom = 18.dp), controller = controller,
                onColorChanged = onColorChanged,
                drawDefaultWheelIndicator = false,
                wheelImageBitmap = toImageBitmap(context, R.drawable.color_picker_wheel)
            )
            BrightnessSlider(
                controller = controller,
                modifier = Modifier
                    .height(30.dp)
                    .padding(horizontal = 16.dp),
                wheelImageBitmap = toImageBitmap(context, R.drawable.brightness_slider_wheel),
            )
        }
    }
}