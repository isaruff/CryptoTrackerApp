package com.isaruff.cryptotrackerapp.domain.model

import com.isaruff.cryptotrackerapp.common.enums.CurrencyTypes


data class CoinListModel(
    val id: String,
    val image: String,
    val lastUpdated: String,
    val name: String,
    val currentPrice: Double,
    val currency: String,
)