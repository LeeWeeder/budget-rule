package com.ljmaq.budgetrule.presentation.home

data class DialogState(
    val deletePartitionWarningDialogShowing: Boolean = false,
    val newPartitionDialogShowing: Boolean = false,
    val updatePartitionDialogShowing: Boolean = false
)