package com.isaruff.cryptotrackerapp.domain.model

data class CoinListCacheModel(
    val id: String,
    val image: String,
    val lastUpdated: String,
    val name: String,
    val currentPrice: Double,
    val currency: String
)