package com.ljmaq.budgetrule.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import com.ljmaq.budgetrule.presentation.onboarding.page.IntroPage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen() {
    val pagerState = rememberPagerState(pageCount = {
        3
    })
    HorizontalPager(state = pagerState) { page ->
        when (page) {
            1 -> IntroPage()
        }
    }
}