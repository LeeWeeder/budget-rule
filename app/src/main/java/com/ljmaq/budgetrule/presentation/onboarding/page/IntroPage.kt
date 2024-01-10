package com.ljmaq.budgetrule.presentation.onboarding.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ljmaq.budgetrule.R

@Composable
fun IntroPage() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.intro_animation))
    LottieAnimation(composition = composition)
}