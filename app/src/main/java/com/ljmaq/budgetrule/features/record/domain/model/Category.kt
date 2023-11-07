package com.ljmaq.budgetrule.features.record.domain.model

import androidx.compose.ui.graphics.Color

sealed class Category(
    val name: String,
    val contentColor: Color,
    val percentage: Float,
    val amount: Double = 1000.00
) {
    data object Needs: Category(name = "Needs", contentColor = Color(0xffdce1ff), percentage = 0.4f)
    data object Wants: Category(name = "Wants", contentColor = Color(0xffffdbd1), percentage = 0.3f)
    data object Savings: Category(name = "Savings", contentColor = Color(0xff80f8ce), percentage = 0.2f)
    data object Investments: Category(name = "Investments", contentColor = Color.Cyan, percentage = 0.1f)
}
