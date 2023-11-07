package com.ljmaq.budgetrule.features.record.presentation.records.util

import android.icu.text.NumberFormat
import android.icu.util.Calendar
import android.icu.util.Currency

class Formatter {
    companion object {
        fun formatDate(timestamp: Long): String {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timestamp

            val month = when (calendar.get(Calendar.MONTH)) {
                0 -> "Jan"
                1 -> "Feb"
                2 -> "Mar"
                3 -> "Apr"
                4 -> "May"
                5 -> "Jun"
                6 -> "Jul"
                7 -> "Aug"
                8 -> "Sep"
                9 -> "Oct"
                10 -> "Nov"
                else -> "Dec"
            }

            val current = Calendar.getInstance()
            current.timeInMillis = System.currentTimeMillis()

            if (calendar.get(Calendar.DATE) == current.get(Calendar.DATE)) {
                return "Today"
            }
            return "$month ${calendar.get(Calendar.DATE)}"
        }

        fun formatCurrency(amount: String): String {
            val currency = NumberFormat.getCurrencyInstance()
            currency.currency = Currency.getInstance("PHP")
            return currency.format(amount.toDouble())
        }

        fun formatNumber(amount: String): String {
            val integer: String
            var decimal = ""
            if (amount == "Error") return amount
            if (amount.contains('.')) {
                decimal = amount.substringAfter('.')
                if (amount.substringBefore('.').length > 3) {
                    integer = amount.substringBefore('.')
                } else {
                    return amount
                }
            } else {
                if (amount.length > 3) integer = amount else return amount
            }

            var formatted = ""

            integer.reversed().chunked(3).forEachIndexed { index, s ->
                formatted += if (index == 0)
                    s
                else
                    ",$s"
            }

            return if (amount.contains('.')) "${formatted.reversed()}.$decimal" else formatted.reversed()
        }
    }
}