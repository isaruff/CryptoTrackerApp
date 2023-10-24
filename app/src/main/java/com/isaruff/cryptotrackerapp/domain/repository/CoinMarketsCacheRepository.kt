package com.isaruff.cryptotrackerapp.domain.repository

import com.isaruff.cryptotrackerapp.data.local.entities.CoinMarketsCacheEntity
import kotlinx.coroutines.flow.Flow

interface CoinMarketsCacheRepository {
    suspend fun getMarketsListByCurrency(currency: String): Flow<List<CoinMarketsCacheEntity>>


    suspend fun upsertCoinMarkets(coinMarket: CoinMarketsCacheEntity)

    fun getMarketsByUniqueId(id: String): Flow<CoinMarketsCacheEntity>

}