package com.isaruff.cryptotrackerapp.domain.use_cases

import com.isaruff.cryptotrackerapp.data.local.entities.CoinMarketsCacheEntity
import com.isaruff.cryptotrackerapp.data.local.repository.CoinMarketsCacheRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCacheCoinsUseCase @Inject constructor(
    private val cacheRepository: CoinMarketsCacheRepository
) {

    suspend operator fun invoke(currency: String): Flow<List<CoinMarketsCacheEntity>>{
        return cacheRepository.getMarketsListByCurrency(currency)
    }

}