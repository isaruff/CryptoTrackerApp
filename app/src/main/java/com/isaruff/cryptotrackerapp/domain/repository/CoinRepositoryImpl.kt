package com.isaruff.cryptotrackerapp.domain.repository

import com.isaruff.cryptotrackerapp.data.dto.CoinDataRequestModel
import com.isaruff.cryptotrackerapp.data.dto.CoinMarketResponse
import com.isaruff.cryptotrackerapp.data.dto.CoinMarketsRequestModel
import com.isaruff.cryptotrackerapp.data.remote.CoinGeckoService
import com.isaruff.cryptotrackerapp.data.repository.CoinRepository
import retrofit2.Response
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coinApi: CoinGeckoService
): CoinRepository {
    override suspend fun getCoinMarkets(body: CoinMarketsRequestModel): Response<List<CoinMarketResponse>> {
        return coinApi.getCoinMarkets(body)
    }

    override suspend fun getCoinData(body: CoinDataRequestModel): Response<String> {
        return coinApi.getCoinData(body)
    }

}