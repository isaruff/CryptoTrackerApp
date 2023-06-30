package com.isaruff.cryptotrackerapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.isaruff.cryptotrackerapp.data.local.entities.TrackedCoinEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface TrackedCoinDao {

    @Query("select * from tracked_coin_table")
    fun getAllTrackedCoins(): Flow<List<TrackedCoinEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackableCoin(coin: TrackedCoinEntity)

    @Delete
    suspend fun deleteTrackableCoin(coin: TrackedCoinEntity)

    @Update
    suspend fun deleteTrackedCoin(coin: TrackedCoinEntity)
}