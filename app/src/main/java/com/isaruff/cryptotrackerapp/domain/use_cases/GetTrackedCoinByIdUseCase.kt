package com.isaruff.cryptotrackerapp.domain.use_cases

import com.isaruff.cryptotrackerapp.data.local.entities.TrackedCoinEntity
import com.isaruff.cryptotrackerapp.data.local.repository.TrackedCoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrackedCoinByIdUseCase @Inject constructor(
    private val trackedCoinRepo: TrackedCoinRepository
) {
    suspend operator fun invoke(id: String): Flow<TrackedCoinEntity>{
        return trackedCoinRepo.getTrackedCoinById(id)
    }
}