package com.ljmaq.budgetrule.features.record.presentation.records

import com.ljmaq.budgetrule.features.record.domain.model.Category
import com.ljmaq.budgetrule.features.record.domain.model.Record

sealed class RecordsEvent {
    data class DeleteRecord(val record: Record): RecordsEvent()
    data object RestoreRecord: RecordsEvent()
    data class ChangeCategory(val category: Category): RecordsEvent()
    data object CreateRecord: RecordsEvent()
    data object CancelCreateRecord: RecordsEvent()
    data object ChangeSelectionMode: RecordsEvent()
    data class AddToSelection(val record: Record): RecordsEvent()
    data object ResetRecentlyDeletedRecord: RecordsEvent()
    data object AddAllToSelection: RecordsEvent()
    data object RemoveAllFromSelection: RecordsEvent()
}