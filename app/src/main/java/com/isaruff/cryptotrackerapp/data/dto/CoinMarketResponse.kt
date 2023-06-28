package com.isaruff.cryptotrackerapp.data.dto

import com.google.gson.annotations.SerializedName


data class CoinMarketResponse(
    val ath: Double,
    @SerializedName("ath_change_percentage")
    val athChangePercentage: Double,
    @SerializedName("ath_date")
    val athDate: String,
    val atl: Double,
    @SerializedName("atl_change_percentage")
    val atlChangePercentage: Double,
    @SerializedName("atl_date")
    val atlDate: String,
    @SerializedName("circulating_supply")
    val circulatingSupply: Int,
    @SerializedName("current_price")
    val currentPrice: Int,
    @SerializedName("fully_diluted_valuation")
    val fullyDilutedValuation: Int,
    @SerializedName("high_24h")
    val high24h: Int,
    val id: String,
    val image: String,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("low_24h")
    val low24h: Int,
    @SerializedName("market_cap")
    val marketCap: Int,
    @SerializedName("market_cap_change_24h")
    val marketCapChange24h: Int,
    @SerializedName("market_cap_change_percentage_24h")
    val marketCapChangePercentage24h: Double,
    @SerializedName("market_cap_rank")
    val marketCapRange: Int,
    @SerializedName("max_supply")
    val maxSupply: Int,
    val name: String,
    @SerializedName("price_change_24h")
    val priceChange24h: Int,
    @SerializedName("price_change_percentage_1h_in_currency")
    val priceChangePercentage1hInCurrency: Int,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Int,
    val roi: Any,
    val symbol: String,
    @SerializedName("total_supply")
    val totalSupply: Int,
    @SerializedName("total_volume")
    val totalVolume: Int
)