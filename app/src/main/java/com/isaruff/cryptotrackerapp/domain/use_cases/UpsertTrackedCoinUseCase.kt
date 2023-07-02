package com.isaruff.cryptotrackerapp.domain.use_cases

import com.isaruff.cryptotrackerapp.data.local.entities.TrackedCoinEntity
import com.isaruff.cryptotrackerapp.data.local.repository.TrackedCoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpsertTrackedCoinUseCase @Inject constructor(
    private val trackedCoinRepo: TrackedCoinRepository
) {
    suspend operator fun invoke(entity: TrackedCoinEntity) {
        trackedCoinRepo.upsertTrackableCoin(entity)
    }
}