package com.ljmaq.budgetrule.presentation.partition

import com.ljmaq.budgetrule.domain.model.Partition

data class PartitionState(
    val partitionList: List<Partition> = emptyList()
)