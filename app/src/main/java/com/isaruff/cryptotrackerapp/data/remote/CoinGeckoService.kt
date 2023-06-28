package com.isaruff.cryptotrackerapp.data.remote

import com.isaruff.cryptotrackerapp.data.dto.CoinDataRequestModel
import com.isaruff.cryptotrackerapp.data.dto.CoinMarketResponse
import com.isaruff.cryptotrackerapp.data.dto.CoinMarketsRequestModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface CoinGeckoService {

    @GET("coin/markets")
    suspend fun getCoinMarkets(@Body body: CoinMarketsRequestModel): Response<List<CoinMarketResponse>>

    @GET("simple/price")
    suspend fun getCoinData(@Body body : CoinDataRequestModel): Response<String>
}