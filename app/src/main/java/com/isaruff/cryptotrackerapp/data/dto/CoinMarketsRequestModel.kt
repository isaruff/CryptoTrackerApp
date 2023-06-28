package com.isaruff.cryptotrackerapp.data.dto

import com.google.gson.annotations.SerializedName


data class CoinMarketsRequestModel(
    @SerializedName("vs_currency")
    val currency: String,
    val ids: String,
    var order: String = "market_cap_asc",
    @SerializedName("per_page")
    var perPage: Int? = null,
    var sparkline: Boolean = false,
    var locate: String = "en",
    var precision: String? = null
)
