package com.ljmaq.budgetrule.presentation.partition

sealed class PartitionEvent {
    data object Loading: PartitionEvent()
    data object FinishedLoading: PartitionEvent()
}