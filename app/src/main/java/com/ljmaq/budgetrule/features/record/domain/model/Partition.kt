package com.ljmaq.budgetrule.features.record.domain.model

import androidx.compose.ui.graphics.Color

sealed class Partition(
    val name: String,
    val contentColor: Color,
    open val amount: Double = 0.00,
    val partitionValue: Double
) {
    data class Needs(override val amount: Double = 0.0) : Partition(
        name = "Needs",
        contentColor = Color(0xffdce1ff),
        amount = amount,
        partitionValue = 0.5
    )

    data class Wants(override val amount: Double = 0.0) : Partition(
        name = "Wants",
        contentColor = Color(0xffffdbd1),
        amount = amount,
        partitionValue = 0.2
    )

    data class Savings(override val amount: Double = 0.0) : Partition(
        name = "Savings",
        contentColor = Color(0xff80f8ce),
        amount = amount,
        partitionValue = 0.2
    )

    data class Investments(override val amount: Double = 0.0) : Partition(
        name = "Investments",
        contentColor = Color.Cyan,
        amount = amount,
        partitionValue = 0.1
    )
}
