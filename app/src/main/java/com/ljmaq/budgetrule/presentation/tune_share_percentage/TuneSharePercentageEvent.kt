package com.ljmaq.budgetrule.presentation.tune_share_percentage

import com.ljmaq.budgetrule.domain.model.Partition

sealed class TuneSharePercentageEvent {
    data class Initialize(val partitions: List<Partition>, val leftOverPartition: Partition): TuneSharePercentageEvent()
    data class EditSharePercent(val partition: Partition): TuneSharePercentageEvent()
    data class EditLeftOverSharePercent(val partition: Partition): TuneSharePercentageEvent()
    data object SaveTuneSharePercentage: TuneSharePercentageEvent()
}