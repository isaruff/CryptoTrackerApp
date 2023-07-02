package com.isaruff.cryptotrackerapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.isaruff.cryptotrackerapp.data.local.entities.CoinMarketsCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinMarketsCacheDao {
    @Query("select * from coin_markets_cache_table where currency =:currency ")
    fun getMarketsListByCurrency(currency: String): Flow<List<CoinMarketsCacheEntity>>

    @Upsert
    suspend fun upsertCoinMarkets(coinMarket: CoinMarketsCacheEntity)

    @Query("select * from coin_markets_cache_table where idWithCurrency =:id")
    fun getMarketsByUniqueId(id: String): Flow<CoinMarketsCacheEntity>


}