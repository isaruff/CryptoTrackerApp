package com.isaruff.cryptotrackerapp.data.repository

import com.isaruff.cryptotrackerapp.data.dto.CoinDataRequestModel
import com.isaruff.cryptotrackerapp.data.dto.CoinMarketResponse
import com.isaruff.cryptotrackerapp.data.dto.CoinMarketsRequestModel
import retrofit2.Response

interface CoinRepository {

    suspend fun getCoinMarkets(body: CoinMarketsRequestModel): Response<List<CoinMarketResponse>>

    suspend fun getCoinData(body : CoinDataRequestModel): Response<String>
}