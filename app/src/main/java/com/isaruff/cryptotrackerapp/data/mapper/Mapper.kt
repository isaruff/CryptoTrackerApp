package com.isaruff.cryptotrackerapp.data.mapper

import com.isaruff.cryptotrackerapp.data.remote.dto.CoinMarketResponse
import com.isaruff.cryptotrackerapp.domain.model.CoinListModel

fun CoinMarketResponse.toCoinListModel(): CoinListModel {
    return CoinListModel(
        id = id,
        image = image,
        lastUpdated = lastUpdated,
        marketCap = marketCap,
        name = name,
        priceChangePercentage1hInCurrency = priceChangePercentage1hInCurrency,
        currentPrice = currentPrice
    )
}