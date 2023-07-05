package com.isaruff.cryptotrackerapp.data.mapper

import com.isaruff.cryptotrackerapp.data.local.entities.CoinMarketsCacheEntity
import com.isaruff.cryptotrackerapp.data.local.entities.TrackedCoinEntity
import com.isaruff.cryptotrackerapp.data.remote.dto.CoinMarketResponse
import com.isaruff.cryptotrackerapp.domain.model.CoinDetailsModel
import com.isaruff.cryptotrackerapp.domain.model.CoinListModel

fun CoinMarketResponse.toCoinListModel(currency: String): CoinListModel {
    return CoinListModel(
        id = id,
        image = image,
        lastUpdated = lastUpdated,
        name = name,
        currentPrice = currentPrice,
        currency = currency
    )
}

fun CoinMarketsCacheEntity.toCoinListModel(): CoinListModel{
    return CoinListModel(
        id = id,
        image = image,
        lastUpdated = lastUpdated,
        name = name,
        currentPrice = currentPrice,
        currency = currency
    )
}

