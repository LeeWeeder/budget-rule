package com.ljmaq.budgetrule.features.onboarding.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ljmaq.budgetrule.features.onboarding.domain.usecase.OnBoardingUseCases
import com.ljmaq.budgetrule.features.util.Scene
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val useCases: OnBoardingUseCases
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(Scene.OnBoardingScene.route)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            useCases.readOnBoardingState().collect { completed ->
                if (completed) {
                    _startDestination.value = Scene.BudgetRuleAppScene.route
                } else {
                    _startDestination.value = Scene.OnBoardingScene.route
                }
            }
            _isLoading.value = false
        }
    }

}