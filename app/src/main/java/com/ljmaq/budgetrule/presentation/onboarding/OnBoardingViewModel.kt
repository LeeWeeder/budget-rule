package com.ljmaq.budgetrule.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ljmaq.budgetrule.domain.model.Partition
import com.ljmaq.budgetrule.domain.usecase.DataStoreUseCases
import com.ljmaq.budgetrule.domain.usecase.PartitionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val dataStoreUseCases: DataStoreUseCases,
    private val partitionUseCases: PartitionUseCases
) : ViewModel() {
//    val locale = Locale.getAvailableLocales()
//    val currency = mutableSetOf<Currency>()
//    for (i in locale.indices) {
//        try {
//            currency.add(Currency.getInstance(locale[i]))
//        } catch (e: IllegalArgumentException) {
//            continue
//        }
//    }
//    println(currency)

    fun onEvent(event: OnBoardingEvent) {
        when (event) {
            is OnBoardingEvent.Initialize -> {
                val initialBalance = event.initialBalance
                val basicPartitions = listOf(
                    Partition(
                        name = "Needs",
                        amount = initialBalance * 0.4,
                        sharePercent = 0.4f,
                        avatar = "🏠",
                        color = 0xFFFFABF3
                    ),
                    Partition(
                        name = "Wants",
                        amount = initialBalance * 0.2,
                        sharePercent = 0.2f,
                        avatar = "🛍️",
                        color = 0xFFFFB3B3
                    ),
                    Partition(
                        name = "Savings",
                        amount = initialBalance * 0.2,
                        sharePercent = 0.2f,
                        avatar = "🏦",
                        color = 0xFF3ADCCC
                    ),
                    Partition(
                        name = "Investment",
                        amount = initialBalance * 0.2,
                        sharePercent = 0.2f,
                        avatar = "📈",
                        color = 0xFFBFC2FF
                    )
                )
                viewModelScope.launch {
                    dataStoreUseCases.saveCurrencyState(event.currencyCode)
                    dataStoreUseCases.saveBalanceState(initialBalance)
                    dataStoreUseCases.saveOnBoardingState(showOnBoarding = false)
                    basicPartitions.forEach { partition ->
                        partitionUseCases.insertPartition(partition)
                        dataStoreUseCases.saveExcessPartitionState(amount = 0.0, sharePercent = 0f)
                    }
                }
            }
        }
    }
}