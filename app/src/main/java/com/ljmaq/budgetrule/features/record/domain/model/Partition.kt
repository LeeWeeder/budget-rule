package com.ljmaq.budgetrule.features.record.domain.model

import androidx.compose.ui.graphics.Color

sealed class Partition(
    val name: String,
    val contentColor: Color,
    val amount: Double = 0.00
) {
    data object Needs: Partition(name = "Needs", contentColor = Color(0xffdce1ff))
    data object Wants: Partition(name = "Wants", contentColor = Color(0xffffdbd1))
    data object Savings: Partition(name = "Savings", contentColor = Color(0xff80f8ce))
    data object Investments: Partition(name = "Investments", contentColor = Color.Cyan)

    companion object {
        val partitions = listOf(Needs, Wants, Savings, Investments)
    }
}
