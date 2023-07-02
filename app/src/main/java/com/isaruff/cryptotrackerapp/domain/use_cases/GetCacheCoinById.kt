package com.isaruff.cryptotrackerapp.domain.use_cases

import com.isaruff.cryptotrackerapp.data.local.entities.CoinMarketsCacheEntity
import com.isaruff.cryptotrackerapp.data.local.repository.CoinMarketsCacheRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCacheCoinById @Inject constructor(
    private val cacheRepo: CoinMarketsCacheRepository
) {

    operator fun invoke(idWithCurrency: String): Flow<CoinMarketsCacheEntity>{
        return cacheRepo.getMarketsByUniqueId(idWithCurrency)
    }
}