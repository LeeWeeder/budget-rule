package com.ljmaq.budgetrule.presentation.tune_share_percentage

import com.ljmaq.budgetrule.domain.model.Partition

data class TuneSharePercentageState(
    val partitions: List<Partition> = emptyList()
)