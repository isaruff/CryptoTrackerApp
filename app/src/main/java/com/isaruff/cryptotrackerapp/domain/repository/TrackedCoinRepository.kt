package com.isaruff.cryptotrackerapp.domain.repository

import com.isaruff.cryptotrackerapp.data.local.entities.TrackedCoinEntity
import kotlinx.coroutines.flow.Flow

interface TrackedCoinRepository {

    suspend fun getTrackedCoinById(id: String): Flow<TrackedCoinEntity>

    suspend fun upsertTrackableCoin(coin: TrackedCoinEntity)

    suspend fun deleteTrackableCoin(coin: TrackedCoinEntity)
}