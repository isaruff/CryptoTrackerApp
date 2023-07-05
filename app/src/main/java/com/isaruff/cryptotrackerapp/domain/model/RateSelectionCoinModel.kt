package com.isaruff.cryptotrackerapp.domain.model

data class RateSelectionCoinModel(
    val coinName: String,
    val image: String,
    val minValue: Double,
    val maxValue: Double
)