package com.ljmaq.budgetrule.features.record.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ljmaq.budgetrule.features.record.domain.model.Income
import com.ljmaq.budgetrule.features.record.domain.usecase.income.IncomesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val incomesUseCases: IncomesUseCases,
) : ViewModel() {
    private val _state = mutableStateOf(IncomeState())
    val state: State<IncomeState> = _state

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

    var currentIncome: Income? = null

    init {
        getIncomes()
    }

    fun onEvent(event: HomeEvent) {
        fun clearRecentlyDeletedIncomeList() {
            recentlyDeletedIncome.clear()
        }

        when (event) {
            is HomeEvent.DeleteIncome -> {
                viewModelScope.launch {
                    event.incomes.forEach { income ->
                        incomesUseCases.deleteIncome(income)
                        recentlyDeletedIncome.add(income)
                    }
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

            is HomeEvent.CancelCreateRecord -> {
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
}