package com.isaruff.cryptotrackerapp.data.remote.repository

import com.isaruff.cryptotrackerapp.data.remote.dto.CoinMarketResponse
import com.isaruff.cryptotrackerapp.data.remote.dto.CoinMarketsDto
import com.isaruff.cryptotrackerapp.data.remote.service.CoinGeckoService
import com.isaruff.cryptotrackerapp.domain.repository.CoinRepository
import retrofit2.Response
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coinApi: CoinGeckoService
) : CoinRepository {
    override suspend fun getCoinMarkets(params: CoinMarketsDto): Response<List<CoinMarketResponse>> {
        return coinApi.getCoinMarkets(
            currency = params.currency,
            ids = params.ids,
            order = params.order,
            sparkline = params.sparkline,
            perPage = params.perPage,
            page = params.page,
            priceChangePercentage = params.priceChangePercentage,
            locale = params.locale,
            precision = params.precision
        )
    }

    override suspend fun getSimpleCoinData(currencies: String, ids: String): okhttp3.ResponseBody {
        return coinApi.getSimpleCoinData(currencies, ids)
    }


}