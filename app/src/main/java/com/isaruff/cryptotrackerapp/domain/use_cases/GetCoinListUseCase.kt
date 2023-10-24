package com.isaruff.cryptotrackerapp.domain.use_cases

import com.isaruff.cryptotrackerapp.common.Resource
import com.isaruff.cryptotrackerapp.data.remote.dto.CoinMarketResponse
import com.isaruff.cryptotrackerapp.data.remote.dto.CoinMarketsDto
import com.isaruff.cryptotrackerapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinListUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(params: CoinMarketsDto): Flow<Resource<List<CoinMarketResponse>>> =
        flow {
            try {
                emit(Resource.Loading())
                val result = repository.getCoinMarkets(params)
                if (result.isSuccessful) {
                    emit(Resource.Success(data =result.body()))
                } else {
                    emit(
                        Resource.Error(
                            errorCode = result.code(),
                            errorDescription = result.errorBody().toString()
                        )
                    )
                }
            } catch (e: HttpException) {
                emit(Resource.Error(0, "Unknown HttpException $e"))
            } catch (e: IOException) {
                emit(Resource.Error(0, "Unknown IO Exception $e"))
            }
        }
}