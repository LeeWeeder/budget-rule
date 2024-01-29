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

        /**
         * Formats a given amount into a specific currency format.
         *
         * @param amount The amount to be formatted. This is of type Double.
         * @param isoCode The ISO 4217 code of the currency. This is of type String.
         * @param useSymbol A Boolean flag that determines whether to use the currency symbol or not. It is optional and defaults to true.
         *
         * @return A string representing the formatted currency. If 'useSymbol' is true, the currency symbol is used. Otherwise, the ISO code is used.
         *
         * Usage:
         * formatCurrency(1234.56, "USD", true)  // Returns "$1,234.56"
         * formatCurrency(1234.56, "USD", false) // Returns "USD 1,234.56"
         */
        fun formatCurrency(amount: Double, isoCode: String, useSymbol: Boolean = true): String {
            val currency = NumberFormat.getCurrencyInstance()
            try {
                currency.currency = Currency.getInstance(isoCode)
            } catch (e: IllegalArgumentException) {
                println(isoCode)
            }
            val str = currency.format(amount)
            if (useSymbol)
                return str
            return str.replace(currency.currency.symbol, currency.currency.currencyCode + " ")
        }

    }
}