package com.revolut.exrate.domain.entity

data class RateItem(
    val code: String,
    val label: String,
    val icon: Int,
    var rate: Double?,
    var editable: Boolean = false,
    var selectionIndex: Int = 0
)