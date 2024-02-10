package com.ljmaq.budgetrule.presentation.tune_share_percentage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ljmaq.budgetrule.domain.model.Partition
import com.ljmaq.budgetrule.domain.usecase.DataStoreUseCases
import com.ljmaq.budgetrule.domain.usecase.PartitionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TuneSharePercentageViewModel @Inject constructor(
    private val partitionUseCases: PartitionUseCases,
    private val dataStoreUseCases: DataStoreUseCases
) : ViewModel() {
    private val _partitions = mutableStateOf(TuneSharePercentageState())
    val partitions: State<TuneSharePercentageState> = _partitions

    private val _leftOverPartitionState =
        mutableStateOf(Partition(amount = 0.0, name = "", sharePercent = 0f))
    val leftOverPartitionState: State<Partition> = _leftOverPartitionState

    fun onEvent(event: TuneSharePercentageEvent) {
        when (event) {
            is TuneSharePercentageEvent.Initialize -> {
                _partitions.value = partitions.value.copy(
                    partitions = event.partitions
                )
                _leftOverPartitionState.value = event.leftOverPartition
            }

            is TuneSharePercentageEvent.EditSharePercent -> {
                _partitions.value = partitions.value.copy(
                    partitions = partitions.value.partitions.map {
                        if (it.id == event.partition.id) {
                            Partition(
                                id = it.id,
                                amount = it.amount,
                                sharePercent = event.partition.sharePercent,
                                name = it.name
                            )
                        } else {
                            it
                        }
                    }
                )
            }

            is TuneSharePercentageEvent.EditLeftOverSharePercent -> {
                _leftOverPartitionState.value = event.partition
            }

            TuneSharePercentageEvent.SaveTuneSharePercentage -> {
                viewModelScope.launch {
                    partitions.value.partitions.map {
                        val partitionTemp = partitionUseCases.getPartitionById(id = it.id)
                        partitionTemp?.let { part ->
                            Partition(
                                id = part.id,
                                amount = part.amount,
                                name = part.name,
                                sharePercent = it.sharePercent,
                                color = part.color,
                                avatar = part.avatar
                            )
                        }
                    }.forEach { partition ->
                        if (partition != null) {
                            partitionUseCases.updatePartition(partition)
                        }
                    }
                }
                viewModelScope.launch(Dispatchers.IO) {
                    val leftOverPartition = leftOverPartitionState.value
                    dataStoreUseCases.saveExcessPartitionState(
                        amount = leftOverPartition.amount,
                        sharePercent = leftOverPartition.sharePercent
                    )
                }
            }
        }
    }
}