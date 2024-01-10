package com.ljmaq.budgetrule.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen() {
    val pagerState = rememberPagerState(pageCount = {
        3
    })
    HorizontalPager(state = pagerState) {

    }
}