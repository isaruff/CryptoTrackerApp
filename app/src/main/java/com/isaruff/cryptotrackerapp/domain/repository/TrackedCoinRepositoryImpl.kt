package com.isaruff.cryptotrackerapp.domain.repository

import com.isaruff.cryptotrackerapp.data.local.dao.TrackedCoinDao
import com.isaruff.cryptotrackerapp.data.local.entities.TrackedCoinEntity
import com.isaruff.cryptotrackerapp.data.local.repository.TrackedCoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrackedCoinRepositoryImpl @Inject constructor(
    private val dao: TrackedCoinDao
) : TrackedCoinRepository {
    override fun getTrackedCoinById(id: String): Flow<TrackedCoinEntity> {
        return dao.getTrackedCoinById(id)
    }


    override suspend fun upsertTrackableCoin(coin: TrackedCoinEntity) {
        dao.upsertTrackableCoin(coin)
    }

    override suspend fun deleteTrackableCoin(coin: TrackedCoinEntity) {
        dao.deleteTrackableCoin(coin)
    }


}