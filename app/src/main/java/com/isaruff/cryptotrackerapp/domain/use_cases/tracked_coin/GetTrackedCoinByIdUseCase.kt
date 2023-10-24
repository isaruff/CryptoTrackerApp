package com.isaruff.cryptotrackerapp.domain.use_cases.tracked_coin

import com.isaruff.cryptotrackerapp.data.local.entities.TrackedCoinEntity
import com.isaruff.cryptotrackerapp.domain.repository.TrackedCoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrackedCoinByIdUseCase @Inject constructor(
    private val trackedCoinRepo: TrackedCoinRepository
) {
    suspend operator fun invoke(id: String): Flow<TrackedCoinEntity>{
        return trackedCoinRepo.getTrackedCoinById(id)
    }
}