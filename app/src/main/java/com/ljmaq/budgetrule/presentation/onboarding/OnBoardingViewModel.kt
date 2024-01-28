package com.ljmaq.budgetrule.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ljmaq.budgetrule.domain.usecase.DataStoreUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val dataStoreUseCases: DataStoreUseCases
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
                viewModelScope.launch {
                    dataStoreUseCases.saveCurrencyState(event.currencyCode)
                    dataStoreUseCases.saveBalanceState(event.initialBalance)
                    dataStoreUseCases.saveOnBoardingState(showOnBoarding = false)
                }
            }
        }
    }
}