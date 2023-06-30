package com.isaruff.cryptotrackerapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.isaruff.cryptotrackerapp.data.local.dao.TrackedCoinDao
import com.isaruff.cryptotrackerapp.data.local.entities.TrackedCoinEntity

@Database(entities = [TrackedCoinEntity::class], version = 1, exportSchema = false)
abstract class CoinDatabase: RoomDatabase() {

    abstract fun getTrackedCoinDao(): TrackedCoinDao
}