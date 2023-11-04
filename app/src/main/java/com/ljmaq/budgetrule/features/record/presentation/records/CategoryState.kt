package com.ljmaq.budgetrule.features.record.presentation.records

import com.ljmaq.budgetrule.features.record.domain.model.Category

data class CategoryState(
    val selectedCategory: Category? = null
)