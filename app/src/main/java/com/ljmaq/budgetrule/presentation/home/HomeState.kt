package com.ljmaq.budgetrule.presentation.home

import com.ljmaq.budgetrule.domain.model.Partition

data class PartitionState(
    val partitionList: List<Partition> = emptyList()
)

data class DialogState(
    val deletePartitionWarningDialogShowing: Boolean = false,
    val newPartitionDialogShowing: Boolean = false,
    val updatePartitionDialogShowing: Boolean = false
)