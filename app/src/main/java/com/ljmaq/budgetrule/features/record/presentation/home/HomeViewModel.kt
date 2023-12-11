package com.ljmaq.budgetrule.features.record.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ljmaq.budgetrule.features.record.domain.model.Income
import com.ljmaq.budgetrule.features.record.domain.model.Partition
import com.ljmaq.budgetrule.features.record.domain.usecase.income.IncomesUseCases
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.investments.InvestmentsUseCases
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.needs.NeedsUseCases
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.savings.SavingsUseCases
import com.ljmaq.budgetrule.features.record.domain.usecase.partition.wants.WantsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val incomesUseCases: IncomesUseCases,
    private val needsUseCases: NeedsUseCases,
    private val wantsUseCases: WantsUseCases,
    private val investmentsUseCases: InvestmentsUseCases,
    private val savingsUseCases: SavingsUseCases
) : ViewModel() {
    private val _state = mutableStateOf(IncomeState())
    val state: State<IncomeState> = _state

    private val _partitionState = mutableStateOf(PartitionState())
    val partitionState: State<PartitionState> = _partitionState

    private val _dialogState = mutableStateOf(DialogState())
    val dialogState: State<DialogState> = _dialogState

    private val _createRecordSheetState = mutableStateOf(false)
    val createRecordSheetState: State<Boolean> = _createRecordSheetState

    private val _isOnSelectionMode = mutableStateOf(false)
    val isOnSelectionMode: State<Boolean> = _isOnSelectionMode

    private val _selectedRecords = mutableStateListOf<Income>()
    val selectedRecords: SnapshotStateList<Income> = _selectedRecords

    private var recentlyDeletedIncome: SnapshotStateList<Income> = mutableStateListOf()

    private var getIncomesJob: Job? = null
    private var getNeedsJob: Job? = null
    private var getWantsJob: Job? = null
    private var getSavingsJob: Job? = null
    private var getInvestmentsJob: Job? = null

    var currentIncome: Income? = null

    init {
        getIncomes()
        getNeeds()
        getWants()
        getSavings()
        getInvestments()
    }

    fun onEvent(event: HomeEvent) {
        fun clearRecentlyDeletedIncomeList() {
            recentlyDeletedIncome.clear()
        }

        when (event) {
            is HomeEvent.DeleteIncome -> {
                viewModelScope.launch {
                    incomesUseCases.deleteIncome(event.income)
                    recentlyDeletedIncome.add(event.income)
                    /*val needs = record.id?.let { needsUseCases.getNeedsById(it) }
                    val wants = record.id?.let { wantsUseCases.getWantsById(it) }
                    val savings = record.id?.let { savingsUseCases.getSavingsById(it) }
                    val investments =
                        record.id?.let { investmentsUseCases.getInvestmentsById(it) }

                    if (needs != null) {
                        needsUseCases.deleteNeeds(needs)
                    }
                    if (wants != null) {
                        wantsUseCases.deleteWants(wants)
                    }
                    if (savings != null) {
                        savingsUseCases.deleteSavings(savings)
                    }
                    if (investments != null) {
                        investmentsUseCases.deleteInvestments(investments)
                    }*/
                }
            }

            is HomeEvent.RestoreIncome -> {
                viewModelScope.launch {
                    recentlyDeletedIncome.forEach { record ->
                        incomesUseCases.insertIncome(record)
                    }
                    clearRecentlyDeletedIncomeList()
                }
            }

            is HomeEvent.PartitionClick -> {
                // TODO: Implement onPartitionClick
            }

            is HomeEvent.CreateRecord -> {
                _createRecordSheetState.value = true
            }

            is HomeEvent.CloseCreateRecordModalBottomSheet -> {
                _createRecordSheetState.value = false
            }

            is HomeEvent.AddToSelection -> {
                if (!selectedRecords.contains(event.income)) {
                    _selectedRecords.add(event.income)
                }
            }

            is HomeEvent.RemoveFromSelection -> {
                if (selectedRecords.contains(event.income)) {
                    _selectedRecords.remove(event.income)
                }

                if (_selectedRecords.size == 0) {
                    onEvent(HomeEvent.ChangeSelectionMode)
                }
            }

            is HomeEvent.ChangeSelectionMode -> {
                if (isOnSelectionMode.value) {
                    _selectedRecords.clear()
                }

                _isOnSelectionMode.value = !isOnSelectionMode.value
            }

            is HomeEvent.AddAllToSelection -> {
                state.value.incomes.forEach { record ->
                    if (!selectedRecords.contains(record)) {
                        _selectedRecords.add(record)
                    }
                }
            }

            is HomeEvent.RemoveAllFromSelection -> {
                _selectedRecords.removeAll(state.value.incomes)
            }

            is HomeEvent.EditIncome -> {
                // TODO: Show edit income sheet
                currentIncome = event.income
            }

            is HomeEvent.DismissDialog -> {
                when (event.dialog) {
                    is Dialog.DeleteWarningAlertDialog -> {
                        _dialogState.value = dialogState.value.copy(
                            deleteWarningDialog = Dialog.DeleteWarningAlertDialog()
                        )
                    }
                }
            }

            HomeEvent.DeleteIconButtonClick -> {
                _dialogState.value = dialogState.value.copy(
                    deleteWarningDialog = Dialog.DeleteWarningAlertDialog(state = true)
                )
            }
        }
    }

    private fun getIncomes() {
        getIncomesJob?.cancel()
        getIncomesJob = incomesUseCases.getIncomesDescending()
            .onEach { incomes ->
                _state.value = state.value.copy(
                    incomes = incomes
                )
            }
            .launchIn(viewModelScope)
    }

    private fun getNeeds() {
        getNeedsJob?.cancel()
        getNeedsJob = needsUseCases.getNeeds().onEach { needs ->
            var sum = 0.0
            needs.forEach {
                sum += it.amount.toDouble()
            }
            _partitionState.value = partitionState.value.copy(
                needs = Partition.Needs(sum)
            )
        }
            .launchIn(viewModelScope)
    }

    private fun getWants() {
        getWantsJob?.cancel()
        getWantsJob = wantsUseCases.getWants().onEach { wants ->
            var sum = 0.0
            wants.forEach {
                sum += it.amount.toDouble()
            }
            _partitionState.value = partitionState.value.copy(
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
            _partitionState.value = partitionState.value.copy(
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
            _partitionState.value = partitionState.value.copy(
                investments = Partition.Investments(sum)
            )
        }
            .launchIn(viewModelScope)
    }
}