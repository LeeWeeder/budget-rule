package com.ljmaq.budgetrule.presentation.home.util

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

        fun formatCurrency(amount: String, isoCode: String): String {
            val currency = NumberFormat.getCurrencyInstance()
            currency.currency = Currency.getInstance(isoCode)
            return currency.format(amount.toDouble())
        }
    }
}