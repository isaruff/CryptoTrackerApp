package com.isaruff.cryptotrackerapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.isaruff.cryptotrackerapp.data.local.entities.TrackedCoinEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface TrackedCoinDao {

    @Query("select * from tracked_coin_table where id=:id")
    fun getTrackedCoinById(id: String): Flow<TrackedCoinEntity>

    @Query("select * from tracked_coin_table")
    fun getTrackedCoinIds(): Flow<List<TrackedCoinEntity>>

    @Upsert
    suspend fun upsertTrackableCoin(coin: TrackedCoinEntity)

    @Delete
    suspend fun deleteTrackableCoin(coin: TrackedCoinEntity)

}