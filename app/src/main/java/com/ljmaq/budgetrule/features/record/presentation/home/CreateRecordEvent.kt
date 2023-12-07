package com.ljmaq.budgetrule.features.record.presentation.home

import androidx.compose.ui.text.input.TextFieldValue
import com.ljmaq.budgetrule.features.record.domain.model.Partition

sealed class CreateRecordEvent {
    data class ChangeRecordType(val recordType: RecordType): CreateRecordEvent()
    data class ChangePartition(val partition: Partition): CreateRecordEvent()
    data class UpdateAmount(val amount: String): CreateRecordEvent()
    data object ResetState: CreateRecordEvent()
}