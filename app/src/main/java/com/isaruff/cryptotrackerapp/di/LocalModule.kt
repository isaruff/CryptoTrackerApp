package com.isaruff.cryptotrackerapp.di

import android.content.Context
import androidx.room.Room
import com.isaruff.cryptotrackerapp.data.local.dao.CoinMarketsCacheDao
import com.isaruff.cryptotrackerapp.data.local.dao.TrackedCoinDao
import com.isaruff.cryptotrackerapp.data.local.room.CoinDatabase
import com.isaruff.cryptotrackerapp.data.local.room.type_converter.ListDoubleConverter
import com.isaruff.cryptotrackerapp.data.local.room.type_converter.ListStringConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Provides
    @Singleton
    fun provideCoinMarketsCacheDao(database: CoinDatabase): CoinMarketsCacheDao {
        return database.getCoinMarketsDao()
    }

    @Provides
    @Singleton
    fun provideTrackedCoinDao(database: CoinDatabase): TrackedCoinDao {
        return database.getTrackedCoinDao()
    }

    @Provides
    @Singleton
    fun provideCoinDatabase(@ApplicationContext context: Context): CoinDatabase {
        return Room.databaseBuilder(context, CoinDatabase::class.java, "coin_database")
            .addTypeConverter(ListStringConverter())
            .addTypeConverter(ListDoubleConverter())
            .build()
    }
}