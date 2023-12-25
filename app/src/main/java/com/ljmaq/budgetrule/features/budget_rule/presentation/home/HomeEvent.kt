package com.ljmaq.budgetrule.features.budget_rule.presentation.home

import com.ljmaq.budgetrule.features.budget_rule.domain.model.Partition

sealed class HomeEvent {
    data class InsertPartition(val partition: Partition): HomeEvent()
    data class DeletePartition(val partition: Partition): HomeEvent()
    data class UpdatePartition(val partition: Partition): HomeEvent()

    data object ShowNewPartitionDialog: HomeEvent()
    data object HideNewPartitionDialog: HomeEvent()
    data object CancelEditPartitionName: HomeEvent()

    data class EditPartitionMenuItemClick(val partition: Partition): HomeEvent()
    data object CreateRecord: HomeEvent()
    data object CancelCreateRecord : HomeEvent()

    data class DeletePartitionMenuItemClick(val partition: Partition): HomeEvent()
    data object HideDeletePartitionDialog: HomeEvent()
}