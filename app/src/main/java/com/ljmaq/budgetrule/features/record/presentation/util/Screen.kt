package com.ljmaq.budgetrule.features.record.presentation.util

sealed class Screen(val route: String) {
    data object RecordScreen: Screen("records_screen")
    data object EditRecordScreen: Screen("edit_record_screen")
}
