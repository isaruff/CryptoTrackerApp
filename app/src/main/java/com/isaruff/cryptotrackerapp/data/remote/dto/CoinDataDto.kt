package com.isaruff.cryptotrackerapp.data.remote.dto

import com.google.gson.annotations.SerializedName


data class CoinDataDto(
    val ids: String,
    @SerializedName("vs_currencies")
    var currencies: String = "btc,eth,xrp",
    @SerializedName("include_market_cap")
    var includeMarketCap: Boolean = false,
    @SerializedName("include_24hr_vol")
    var include24hVol: Boolean = false,
    @SerializedName("include_24hr_change")
    var include24hChange: Boolean = false,
    @SerializedName("include_last_updated_at")
    var includeLastUpdatedAt: Boolean = false,
    var precision: String = "5"


)
