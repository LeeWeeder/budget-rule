package com.ljmaq.budgetrule.domain.usecase

import com.ljmaq.budgetrule.domain.usecase.dataStore.ReadBalanceState
import com.ljmaq.budgetrule.domain.usecase.dataStore.ReadExcessPartitionState
import com.ljmaq.budgetrule.domain.usecase.dataStore.ReadOnBoardingState
import com.ljmaq.budgetrule.domain.usecase.dataStore.SaveBalanceState
import com.ljmaq.budgetrule.domain.usecase.dataStore.SaveExcessPartitionState
import com.ljmaq.budgetrule.domain.usecase.dataStore.SaveOnBoardingState

data class DataStoreUseCases(
    val saveBalanceState: SaveBalanceState,
    val readBalanceState: ReadBalanceState,
    val saveExcessPartitionState: SaveExcessPartitionState,
    val readExcessPartitionState: ReadExcessPartitionState,
    val saveOnBoardingState: SaveOnBoardingState,
    val readOnBoardingState: ReadOnBoardingState
)