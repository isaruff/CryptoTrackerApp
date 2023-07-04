package com.isaruff.cryptotrackerapp.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.isaruff.cryptotrackerapp.common.parseSimpleCoinResponse
import com.isaruff.cryptotrackerapp.data.local.dao.TrackedCoinDao
import com.isaruff.cryptotrackerapp.data.local.entities.TrackedCoinEntity
import com.isaruff.cryptotrackerapp.data.notification.NotificationHandler
import com.isaruff.cryptotrackerapp.data.remote.dto.SimpleCoinResponse
import com.isaruff.cryptotrackerapp.data.remote.service.CoinGeckoService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class CoinTrackerWorker @AssistedInject constructor(
    private val remoteApi: CoinGeckoService,
    private val trackedCoinDao: TrackedCoinDao,
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return handleBackground(context, getTrackedCoinIds())
    }

    private suspend fun getTrackedCoinIds(): String {
        val trackedCoins = trackedCoinDao.getTrackedCoinIds()
        val trackedCoinIds = mutableListOf<String>()
        trackedCoins.collect { list ->
            list.forEach { coinEntity ->
                trackedCoinIds.add(coinEntity.id)
            }
        }
        return trackedCoinIds.joinToString(",")
    }

    private suspend fun handleBackground(context: Context, ids: String): Result {
        val response = remoteApi.getSimpleCoinData(currencies = "usd", ids = ids)
        if (response.isSuccessful) {
            response.body()?.let { jsonString ->
                Log.d("JSON_STRING", jsonString)
                parseSimpleCoinResponse(jsonString).forEach {
                    setCoinHistoryList(it)
                    checkCoinAlertValue(context, it)
                    return Result.success()
                }
            }
        }
        return Result.failure()
    }

    private suspend fun setCoinHistoryList(remoteResponse: SimpleCoinResponse) {
        trackedCoinDao.getTrackedCoinById(remoteResponse.coin).collect { localCoin ->
            val currentList = localCoin.priceList.toMutableList().also {
                it.add(remoteResponse.usd)
            }
            trackedCoinDao.upsertTrackableCoin(
                TrackedCoinEntity(
                    id = localCoin.id,
                    name = localCoin.name,
                    image = localCoin.image,
                    minValue = localCoin.minValue,
                    maxValue = localCoin.maxValue,
                    priceList = currentList,
                    checkpoints = listOf()
                )
            )

        }
    }

    private suspend fun checkCoinAlertValue(context: Context, remoteResponse: SimpleCoinResponse) {
        trackedCoinDao.getTrackedCoinById(remoteResponse.coin).collect {
            if (it.maxValue < remoteResponse.usd || it.minValue > remoteResponse.usd) {
                val notificationText = "Check out coin ${it.name}"
                val notificationTitle = "Alert"
                Log.d("REACHED_COIN_ALERT", "")
                NotificationHandler.showNotification(context, notificationTitle, notificationText)
            }
        }
    }
}