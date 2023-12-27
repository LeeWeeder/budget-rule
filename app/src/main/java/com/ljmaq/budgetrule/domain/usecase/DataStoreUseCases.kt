package com.ljmaq.budgetrule.domain.usecase

import com.ljmaq.budgetrule.domain.usecase.dataStore.ReadBalanceState
import com.ljmaq.budgetrule.domain.usecase.dataStore.SaveBalanceState

data class DataStoreUseCases(
    val saveBalanceState: SaveBalanceState,
    val readBalanceState: ReadBalanceState
)