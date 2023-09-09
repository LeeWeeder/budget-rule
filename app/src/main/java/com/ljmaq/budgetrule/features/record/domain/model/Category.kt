package com.ljmaq.budgetrule.features.record.domain.model

import androidx.compose.ui.graphics.Color

data class Category(
    val name: String,
    val color: Color,
    val amount: String = ""
) {

    companion object {
        val categories = listOf(
            Category(
                name = "NEEDS",
                color = Color.Blue
            ),
            Category(name = "WANTS", color = Color.Red),
            Category(name = "SAVE", color = Color.Green)
        )
    }
}
