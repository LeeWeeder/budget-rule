package com.ljmaq.budgetrule.features.record.presentation.home

import com.ljmaq.budgetrule.features.record.domain.model.Partition

data class CategoryState(
    val selectedCategory: Partition? = null
)