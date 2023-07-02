package com.isaruff.cryptotrackerapp.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.isaruff.cryptotrackerapp.common.parseSimpleCoinResponse
import com.isaruff.cryptotrackerapp.data.local.dao.TrackedCoinDao
import com.isaruff.cryptotrackerapp.data.local.entities.TrackedCoinEntity
import com.isaruff.cryptotrackerapp.data.remote.dto.SimpleCoinResponse
import com.isaruff.cryptotrackerapp.data.remote.service.CoinGeckoService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

//@HiltWorker
//class CoinTrackerWorker @AssistedInject constructor(
//    @Assisted private val remoteApi: CoinGeckoService,
//    @Assisted private val trackedCoinDao: TrackedCoinDao,
//    @Assisted context: Context,
//    @Assisted params: WorkerParameters
//) : CoroutineWorker(context, params) {
//    override suspend fun doWork(): Result {
//        Log.d("LOOOOK_HEREEE", "I AM HEREEE")
//        return Result.success()
//    }
//
//    private suspend fun getTrackedCoinIds(): String {
//        val trackedCoins = trackedCoinDao.getTrackedCoinIds()
//        val trackedCoinIds = mutableListOf<String>()
//        trackedCoins.collect { list ->
//            list.forEach { coinEntity ->
//                trackedCoinIds.add(coinEntity.id)
//            }
//        }
//        return trackedCoinIds.joinToString(",")
//    }
//
//    private suspend fun handleBackground(ids: String) {
//        val response = remoteApi.getSimpleCoinData(currencies = "usd", ids = ids)
//        if (response.isSuccessful) {
//            response.body()?.let {jsonString ->
//                 parseSimpleCoinResponse(jsonString).forEach {
//                     setCoinHistoryList(it)
//                     checkCoinAlertValue(it)
//                 }
//            }
//        }
//    }
//
//    private suspend fun setCoinHistoryList(remoteResponse: SimpleCoinResponse) {
//        trackedCoinDao.getTrackedCoinById(remoteResponse.coin).collect { localCoin ->
//            val currentList = localCoin.priceList.toMutableList().also {
//                it.add(remoteResponse.usd)
//            }
//            trackedCoinDao.upsertTrackableCoin(
//                TrackedCoinEntity(
//                    id = localCoin.id,
//                    name = localCoin.name,
//                    image = localCoin.image,
//                    minValue = localCoin.minValue,
//                    maxValue = localCoin.maxValue,
//                    priceList = currentList,
//                    checkpoints = listOf()
//                )
//            )
//
//        }
//    }
//
//    private suspend fun checkCoinAlertValue(remoteResponse: SimpleCoinResponse){
//        trackedCoinDao.getTrackedCoinById(remoteResponse.coin).collect{
//            if (it.maxValue < remoteResponse.usd || it.minValue > remoteResponse.usd){
//            }
//        }
//    }
//}