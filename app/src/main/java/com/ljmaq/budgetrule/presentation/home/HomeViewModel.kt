package com.ljmaq.budgetrule.presentation.home

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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val partitionUseCases: PartitionUseCases,
    private val dataStoreUseCases: DataStoreUseCases
) : ViewModel() {
    private val _partitionState = mutableStateOf(PartitionState())
    val partitionState: State<PartitionState> = _partitionState

    private val _dialogState = mutableStateOf(DialogState())
    val dialogState: State<DialogState> = _dialogState

    private val _createRecordSheetState = mutableStateOf(false)
    val createRecordSheetState: State<Boolean> = _createRecordSheetState

    private var getPartitionJob: Job? = null

    private val _eventFlow = MutableSharedFlow<CreateRecordViewModel.UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _currentPartition = mutableStateOf<Partition?>(null)
    val currentPartition: State<Partition?> = _currentPartition

    private val _balanceState = MutableStateFlow(0.0)
    val balanceState: StateFlow<Double> = _balanceState

    init {
        getPartition()
        /*getIncomes()
        getNeeds()
        getWants()
        getSavings()
        getInvestments()*/
        viewModelScope.launch(Dispatchers.IO) {
            _balanceState.value = dataStoreUseCases.readBalanceState().stateIn(viewModelScope).value
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.CreateRecord -> {
                _createRecordSheetState.value = true
            }

            is HomeEvent.CancelCreateRecord -> {
                _createRecordSheetState.value = false
            }

            is HomeEvent.DeletePartition -> {
                viewModelScope.launch {
                    try {
                        partitionUseCases.deletePartition(
                            event.partition
                        )
                        _eventFlow.emit(CreateRecordViewModel.UiEvent.SaveRecord)
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            CreateRecordViewModel.UiEvent.ShowSnackbar(
                                e.message ?: "Couldn't save record"
                            )
                        )
                    }
                }
            }

            is HomeEvent.InsertPartition -> {
                viewModelScope.launch {
                    try {
                        partitionUseCases.insertPartition(
                            event.partition
                        )
                        _eventFlow.emit(CreateRecordViewModel.UiEvent.SaveRecord)
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            CreateRecordViewModel.UiEvent.ShowSnackbar(
                                e.message ?: "Couldn't save record"
                            )
                        )
                    }
                }
            }
            is HomeEvent.UpdatePartition -> {
                viewModelScope.launch {
                    try {
                        partitionUseCases.updatePartition(
                            event.partition
                        )
                        _eventFlow.emit(CreateRecordViewModel.UiEvent.SaveRecord)
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            CreateRecordViewModel.UiEvent.ShowSnackbar(
                                e.message ?: "Couldn't save record"
                            )
                        )
                        println(e)
                    }
                }
            }
            HomeEvent.ShowNewPartitionDialog -> {
                _dialogState.value = dialogState.value.copy(
                    newPartitionDialogShowing = true
                )
            }

            HomeEvent.HideNewPartitionDialog -> {
                _dialogState.value = dialogState.value.copy(
                    newPartitionDialogShowing = false
                )
            }

            is HomeEvent.EditPartitionMenuItemClick -> {
                _currentPartition.value = event.partition
                _dialogState.value = dialogState.value.copy(
                    updatePartitionDialogShowing = true
                )
            }

            HomeEvent.CancelEditPartitionName -> {
                _currentPartition.value = null
                _dialogState.value = dialogState.value.copy(
                    updatePartitionDialogShowing = false
                )
            }

            HomeEvent.HideDeletePartitionDialog -> {
                _currentPartition.value = null
                _dialogState.value = dialogState.value.copy(
                    deletePartitionWarningDialogShowing = false
                )
            }
            is HomeEvent.DeletePartitionMenuItemClick -> {
                _currentPartition.value = event.partition
                _dialogState.value = dialogState.value.copy(
                    deletePartitionWarningDialogShowing = true
                )
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
            }
            .launchIn(viewModelScope)
    }

    /*private fun getIncomes() {
        getPartitionJob?.cancel()
        getPartitionJob = incomesUseCases.getAllIncomeDescending()
            .onEach { incomes ->
                _partitionState.value = partitionState.value.copy(
                    incomes = incomes
                )
            }
            .launchIn(viewModelScope)
    }

    private fun getNeeds() {
        getNeedsJob?.cancel()
        getNeedsJob = needsUseCases.getNeedsDescending().onEach { needs ->
            var sum = 0.0
            needs.forEach {
                sum += it.amount.toDouble()
            }
            _needsState.value = needsState.value.copy(
                needsList = needs,
                needs = Partition.Needs(sum)
            )
        }
            .launchIn(viewModelScope)
    }

    private fun getWants() {
        getWantsJob?.cancel()
        getWantsJob = wantsUseCases.getWantsDescending().onEach { wants ->
            var sum = 0.0
            wants.forEach {
                sum += it.amount.toDouble()
            }
            _wantsState.value = wantsState.value.copy(
                wantsList = wants,
                wants = Partition.Wants(sum)
            )
        }
            .launchIn(viewModelScope)
    }

    private fun getSavings() {
        getSavingsJob?.cancel()
        getSavingsJob = savingsUseCases.getSavings().onEach { savings ->
            var sum = 0.0
            savings.forEach {
                sum += it.amount.toDouble()
            }
            _savingsState.value = savingsState.value.copy(
                savingsList = savings,
                savings = Partition.Savings(sum)
            )
        }
            .launchIn(viewModelScope)
    }

    private fun getInvestments() {
        getInvestmentsJob?.cancel()
        getInvestmentsJob = investmentsUseCases.getInvestments().onEach { investments ->
            var sum = 0.0
            investments.forEach {
                sum += it.amount.toDouble()
            }
            _investmentsState.value = investmentsState.value.copy(
                investmentsList = investments,
                investments = Partition.Investments(sum)
            )
        }
            .launchIn(viewModelScope)
    }*/
}