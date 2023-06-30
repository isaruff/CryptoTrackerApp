package com.isaruff.cryptotrackerapp.data.local.repository

import androidx.room.Insert
import androidx.room.Update
import androidx.room.Upsert
import com.isaruff.cryptotrackerapp.data.local.entities.CoinMarketsCacheEntity
import kotlinx.coroutines.flow.Flow

interface CoinMarketsCacheRepository {
    suspend fun getMarketsListByCurrency(currency: String): Flow<List<CoinMarketsCacheEntity>>

    @Upsert
    suspend fun upsertCoinMarkets(coinMarket: CoinMarketsCacheEntity)

}