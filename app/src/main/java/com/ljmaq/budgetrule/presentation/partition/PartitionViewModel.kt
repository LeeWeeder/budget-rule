package com.ljmaq.budgetrule.presentation.partition

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ljmaq.budgetrule.domain.model.Partition
import com.ljmaq.budgetrule.domain.usecase.DataStoreUseCases
import com.ljmaq.budgetrule.domain.usecase.PartitionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartitionViewModel @Inject constructor(
    private val partitionUseCases: PartitionUseCases,
    private val dataStoreUseCases: DataStoreUseCases
) : ViewModel() {
    private val _partitionState = mutableStateOf(PartitionState())
    val partitionState: State<PartitionState> = _partitionState

    private val _uiState = MutableStateFlow(MainActivityUiState.LOADING)
    val uiState = _uiState.asStateFlow()

    private var getPartitionJob: Job? = null

    private val _excessPartitionState =
        MutableStateFlow(Partition(name = "Excess", amount = 0.0, sharePercent = 1f))
    val excessPartitionState: StateFlow<Partition> = _excessPartitionState

    init {
        getPartition()
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreUseCases.readExcessPartitionState().collect {
                _excessPartitionState.value = it
            }
        }
    }

    private fun getPartition() {
        getPartitionJob?.cancel()
        getPartitionJob = partitionUseCases.getAllPartition()
            .onEach { partitions ->
                _partitionState.value = partitionState.value.copy(
                    partitionList = partitions
                )
                _uiState.value = MainActivityUiState.SUCCESS
            }
            .launchIn(viewModelScope)
    }
}

enum class MainActivityUiState {
    LOADING,
    SUCCESS
}