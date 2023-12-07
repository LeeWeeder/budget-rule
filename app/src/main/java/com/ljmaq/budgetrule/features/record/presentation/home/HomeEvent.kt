package com.ljmaq.budgetrule.features.record.presentation.home

import com.ljmaq.budgetrule.features.record.domain.model.Partition
import com.ljmaq.budgetrule.features.record.domain.model.Income

sealed class HomeEvent {

    // Records events
    data class DeleteIncome(val incomes: List<Income>): HomeEvent()
    data object RestoreIncome: HomeEvent()
    data object ChangeSelectionMode: HomeEvent()

    // Partitions events
    data class PartitionClick(val partition: Partition): HomeEvent()

    // Main events
    data object CreateRecord: HomeEvent()
    data class EditIncome(val income: Income): HomeEvent()
    data object CancelCreateRecord: HomeEvent()
    data class AddToSelection(val income: Income): HomeEvent()
    data class RemoveFromSelection(val income: Income): HomeEvent()
    data object AddAllToSelection: HomeEvent()
    data class DismissDialog(val dialog: Dialog): HomeEvent()
    data object RemoveAllFromSelection: HomeEvent()
    data object DeleteIconButtonClick: HomeEvent()
}