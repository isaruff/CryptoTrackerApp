package com.isaruff.cryptotrackerapp.data.remote.dto


data class CoinMarketsDto(
    val currency: String,
    var ids: String? = "",
    var order: String = "market_cap_asc",
    var page: Int?= null,
    var perPage: Int? = null,
    var sparkline: Boolean = true,
    var priceChangePercentage: String? = null,
    var locale: String = "en",
    var precision: String? = null
)
