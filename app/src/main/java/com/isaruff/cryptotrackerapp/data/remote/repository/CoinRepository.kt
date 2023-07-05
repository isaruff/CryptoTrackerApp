package com.isaruff.cryptotrackerapp.data.remote.repository

import com.isaruff.cryptotrackerapp.data.remote.dto.CoinMarketResponse
import com.isaruff.cryptotrackerapp.data.remote.dto.CoinMarketsDto
import retrofit2.Response

interface CoinRepository {

    suspend fun getCoinMarkets(params: CoinMarketsDto): Response<List<CoinMarketResponse>>

    suspend fun getSimpleCoinData(currencies: String = "usd", ids: String): okhttp3.ResponseBody


}