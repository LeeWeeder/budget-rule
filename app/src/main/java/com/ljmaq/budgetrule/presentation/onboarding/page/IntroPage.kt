package com.ljmaq.budgetrule.presentation.onboarding.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ljmaq.budgetrule.R

@Composable
fun IntroPage() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.intro_animation))
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(40.dp, alignment = Alignment.CenterVertically)) {
        LottieAnimation(composition = composition, iterations = LottieConstants.IterateForever, modifier = Modifier.size(300.dp))
        Text(text = "Budget your money, create partitions, automatic")
    }
}