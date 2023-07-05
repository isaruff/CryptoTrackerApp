package com.isaruff.cryptotrackerapp.data.worker

import android.content.Context
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
import kotlinx.coroutines.flow.takeWhile

@HiltWorker
class CoinTrackerWorker @AssistedInject constructor(
    private val remoteApi: CoinGeckoService,
    private val trackedCoinDao: TrackedCoinDao,
    @Assisted context: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(context, params) {

    private var canFetchRequest = true

    override suspend fun doWork(): Result {
        handleBackground()
        return Result.success()
    }

    private suspend fun handleBackground() {
        val trackedCoins = trackedCoinDao.getTrackedCoinIds()
        val trackedCoinIds = mutableSetOf<String>()
        trackedCoins.takeWhile { canFetchRequest }.collect { list ->
            canFetchRequest = false
            list.forEach { coinEntity ->
                trackedCoinIds.add(coinEntity.id)
            }
            val idsJoined = trackedCoinIds.joinToString(",")
            println("IDS ${idsJoined}")
            val response = remoteApi.getSimpleCoinData(currencies = "usd", ids = idsJoined).string()
            println("RESPONSE ${response}")
            parseSimpleCoinResponse(response).forEach { parsedResponse ->
                val trackedCoinEntity = list.find { it.id == parsedResponse.coin }
                checkCoinAlertValue(
                    applicationContext,
                    localCoin = trackedCoinEntity,
                    remoteResponse = parsedResponse
                )
                setCoinHistoryList(localCoin = trackedCoinEntity, remoteResponse = parsedResponse)
            }
        }


    }


    private suspend fun setCoinHistoryList(
        localCoin: TrackedCoinEntity?,
        remoteResponse: SimpleCoinResponse
    ) {
        localCoin?.let {
            val currentList = it.priceList.toMutableList()
            currentList.add(remoteResponse.usd)
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

    private fun checkCoinAlertValue(
        context: Context,
        localCoin: TrackedCoinEntity?,
        remoteResponse: SimpleCoinResponse
    ) {
        localCoin?.let {
            if (it.maxValue < remoteResponse.usd || it.minValue > remoteResponse.usd) {
                val notificationText = "Check out coin ${localCoin.name}"
                val notificationTitle = "Alert"
                NotificationHandler.showNotification(context, notificationTitle, notificationText)
            }
        }


    }
}