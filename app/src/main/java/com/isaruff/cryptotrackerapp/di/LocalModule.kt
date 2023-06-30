package com.isaruff.cryptotrackerapp.di

import android.content.Context
import androidx.room.Room
import com.isaruff.cryptotrackerapp.data.local.dao.TrackedCoinDao
import com.isaruff.cryptotrackerapp.data.local.room.CoinDatabase
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
    fun provideTrackedCoinDao(database: CoinDatabase): TrackedCoinDao{
        return database.getTrackedCoinDao()
    }

    @Provides
    @Singleton
    fun provideCoinDatabase(@ApplicationContext context: Context): CoinDatabase{
        return Room.databaseBuilder(context, CoinDatabase::class.java, "coin_database").build()
    }
}