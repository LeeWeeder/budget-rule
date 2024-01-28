package com.ljmaq.budgetrule.presentation.onboarding

sealed class OnBoardingEvent {
    data class Initialize(val currencyCode: String, val initialBalance: Double) : OnBoardingEvent()
}
