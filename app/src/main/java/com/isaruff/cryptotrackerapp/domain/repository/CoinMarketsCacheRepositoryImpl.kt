package com.isaruff.cryptotrackerapp.domain.repository

import com.isaruff.cryptotrackerapp.data.local.dao.CoinMarketsCacheDao
import com.isaruff.cryptotrackerapp.data.local.entities.CoinMarketsCacheEntity
import com.isaruff.cryptotrackerapp.data.local.repository.CoinMarketsCacheRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoinMarketsCacheRepositoryImpl @Inject constructor(
    private val dao: CoinMarketsCacheDao
): CoinMarketsCacheRepository {
    override suspend fun getMarketsListByCurrency(currency: String): Flow<List<CoinMarketsCacheEntity>> {
        return dao.getMarketsListByCurrency(currency)
    }

    override suspend fun upsertCoinMarkets(coinMarket: CoinMarketsCacheEntity) {
        dao.upsertCoinMarkets(coinMarket)
    }


}