package com.ljmaq.budgetrule.features.record.domain.util

fun String.isDigitsOnly(): Boolean {
    return try {
        this.toFloat()
        true
    } catch (e: NumberFormatException) {
        false
    }
}