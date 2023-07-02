package com.isaruff.cryptotrackerapp.domain.model

import com.google.gson.annotations.SerializedName
import com.isaruff.cryptotrackerapp.data.remote.dto.SparklineList

data class CoinListModel(
    val id: String,
    val image: String,
    val lastUpdated: String,
    val name: String,
    val currentPrice: Double,
    val currency: String,
    val sparklineList: List<Double>
)