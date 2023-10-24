package com.isaruff.cryptotrackerapp.domain.use_cases.tracked_coin

import com.isaruff.cryptotrackerapp.data.local.entities.TrackedCoinEntity
import com.isaruff.cryptotrackerapp.domain.repository.TrackedCoinRepository
import javax.inject.Inject

class DeleteTrackedCoinUseCase @Inject constructor(
    private val trackedCoinRepo: TrackedCoinRepository
) {

    suspend operator fun invoke(entity: TrackedCoinEntity){
        trackedCoinRepo.deleteTrackableCoin(coin = entity)
    }
}