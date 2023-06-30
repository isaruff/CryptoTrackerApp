package com.isaruff.cryptotrackerapp.data.remote.service

import com.isaruff.cryptotrackerapp.data.remote.dto.CoinMarketResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinGeckoService {

    @GET("coins/markets")
    suspend fun getCoinMarkets(
        @Query("vs_currency") currency: String,
        @Query("ids") ids: String? = "bitcoin",
        @Query("order") order: String?,
        @Query("per_page") perPage: Int?,
        @Query("page") page: Int?,
        @Query("price_change_percentage") priceChangePercentage: String?,
        @Query("locale") locale: String?,
        @Query("precision") precision: String?
    ): Response<List<CoinMarketResponse>>

    @GET("simple/price")
    suspend fun getCoinData(): Response<String>
}