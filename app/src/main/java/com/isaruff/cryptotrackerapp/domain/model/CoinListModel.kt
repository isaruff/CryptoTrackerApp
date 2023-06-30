package com.isaruff.cryptotrackerapp.domain.model

import com.google.gson.annotations.SerializedName

data class CoinListModel(
    val id: String,
    val image: String,
    val lastUpdated: String,
    val marketCap: Double,
    val name: String,
    val priceChangePercentage1hInCurrency: Double,
    val currentPrice: Double

)