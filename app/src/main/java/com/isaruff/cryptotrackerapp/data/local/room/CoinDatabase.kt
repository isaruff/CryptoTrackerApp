package com.isaruff.cryptotrackerapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.isaruff.cryptotrackerapp.data.local.dao.CoinMarketsCacheDao
import com.isaruff.cryptotrackerapp.data.local.dao.TrackedCoinDao
import com.isaruff.cryptotrackerapp.data.local.entities.CoinMarketsCacheEntity
import com.isaruff.cryptotrackerapp.data.local.entities.TrackedCoinEntity
import com.isaruff.cryptotrackerapp.data.local.type_converter.ListDoubleConverter
import com.isaruff.cryptotrackerapp.data.local.type_converter.ListStringConverter

@Database(entities = [TrackedCoinEntity::class, CoinMarketsCacheEntity::class], version = 1, exportSchema = false)
@TypeConverters(ListDoubleConverter::class, ListStringConverter::class)
abstract class CoinDatabase: RoomDatabase() {

    abstract fun getTrackedCoinDao(): TrackedCoinDao

    abstract fun getCoinMarketsDao(): CoinMarketsCacheDao
}