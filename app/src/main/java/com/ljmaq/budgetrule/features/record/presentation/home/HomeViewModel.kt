package com.ljmaq.budgetrule.features.record.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ljmaq.budgetrule.features.record.domain.model.Income
import com.ljmaq.budgetrule.features.record.domain.model.Partition
import com.ljmaq.budgetrule.features.record.domain.model.partition.Investments
import com.ljmaq.budgetrule.features.record.domain.model.partition.Needs
import com.ljmaq.budgetrule.features.record.domain.model.partition.Savings
import com.ljmaq.budgetrule.features.record.domain.model.partition.Wants
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
    private val _incomeState = mutableStateOf(IncomeState())
    val incomeState: State<IncomeState> = _incomeState

    private val _needsState = mutableStateOf(NeedsState())
    val needsState: State<NeedsState> = _needsState

    private val _wantsState = mutableStateOf(WantsState())
    val wantsState: State<WantsState> = _wantsState

    private val _savingsState = mutableStateOf(SavingsState())
    val savingsState: State<SavingsState> = _savingsState

    private val _investmentsState = mutableStateOf(InvestmentsState())
    val investmentsState: State<InvestmentsState> = _investmentsState

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
        when (event) {
            is HomeEvent.DeleteIncome -> {
                viewModelScope.launch {
                    incomesUseCases.deleteIncome(event.income)
                    recentlyDeletedIncome.add(event.income)
                    val needs = event.income.timestamp.let { needsUseCases.getNeedsById(it) }
                    val wants = event.income.timestamp.let { wantsUseCases.getWantsById(it) }
                    val savings = event.income.timestamp.let { savingsUseCases.getSavingsById(it) }
                    val investments =
                        event.income.timestamp.let { investmentsUseCases.getInvestmentsById(it) }

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
                    }
                }
            }

            is HomeEvent.RestoreIncome -> {
                viewModelScope.launch {
                    recentlyDeletedIncome.forEach {
                        val timestamp = it.timestamp
                        val amount = it.amount.toDouble()
                        incomesUseCases.insertIncome(it)
                        needsUseCases.insertNeeds(Needs(timestamp, (amount * Partition.Needs().partitionValue).toString()))
                        wantsUseCases.insertWants(Wants(timestamp, (amount * Partition.Wants().partitionValue).toString()))
                        savingsUseCases.insertSavings(Savings(timestamp, (amount * Partition.Savings().partitionValue).toString()))
                        investmentsUseCases.insertInvestments(Investments(timestamp, (amount * Partition.Investments().partitionValue).toString()))
                    }

                    recentlyDeletedIncome.clear()
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
                incomeState.value.incomes.forEach { record ->
                    if (!selectedRecords.contains(record)) {
                        _selectedRecords.add(record)
                    }
                }
            }

            is HomeEvent.RemoveAllFromSelection -> {
                _selectedRecords.removeAll(incomeState.value.incomes)
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
                _incomeState.value = incomeState.value.copy(
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
    }
}