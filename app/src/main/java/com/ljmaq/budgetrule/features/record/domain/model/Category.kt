package com.ljmaq.budgetrule.features.record.domain.model

import androidx.compose.ui.graphics.Color

data class Category(
    val name: String,
    val color: Color,
    val amount: String = ""
) {

    companion object {
        val categories = listOf(
            Category(name = "NEEDS", color = Color(0xFF143A9B)),
            Category(name = "WANTS", color = Color(0xFFF05929)),
            Category(name = "SAVE", color = Color(0xFF29AD87))
        )
    }
}
