package com.isaruff.cryptotrackerapp.data.local.repository

import com.isaruff.cryptotrackerapp.data.local.entities.CoinMarketsCacheEntity
import com.isaruff.cryptotrackerapp.data.local.entities.TrackedCoinEntity
import kotlinx.coroutines.flow.Flow

interface TrackedCoinRepository {

    suspend fun getTrackedCoinById(id: String): Flow<TrackedCoinEntity>

    suspend fun upsertTrackableCoin(coin: TrackedCoinEntity)

    suspend fun deleteTrackableCoin(coin: TrackedCoinEntity)
}