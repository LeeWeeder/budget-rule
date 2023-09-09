package com.ljmaq.budgetrule.features.record.presentation.records.util

import java.time.LocalTime

fun getGreetings(): String {
    val time = LocalTime.now()
    return when (time.hour) {
        in 0..11 -> "Good morning"
        in 12..13 -> "Good noon"
        in 14..17 -> "Good afternoon"
        else -> "Good evening"
    }
}