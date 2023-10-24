package com.isaruff.cryptotrackerapp.domain.use_cases.cache_coin

import com.isaruff.cryptotrackerapp.data.local.entities.CoinMarketsCacheEntity
import com.isaruff.cryptotrackerapp.domain.repository.CoinMarketsCacheRepository
import javax.inject.Inject

class UpsertCacheCoinUseCase @Inject constructor(
    private val cacheRepository: CoinMarketsCacheRepository
) {
    suspend operator fun invoke(coinMarketsCacheEntity: CoinMarketsCacheEntity){
        cacheRepository.upsertCoinMarkets(coinMarketsCacheEntity)
    }
}