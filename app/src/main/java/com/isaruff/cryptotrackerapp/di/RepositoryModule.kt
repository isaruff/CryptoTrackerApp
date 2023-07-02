package com.isaruff.cryptotrackerapp.di

import com.isaruff.cryptotrackerapp.data.local.repository.CoinMarketsCacheRepository
import com.isaruff.cryptotrackerapp.data.local.repository.TrackedCoinRepository
import com.isaruff.cryptotrackerapp.data.remote.repository.CoinRepository
import com.isaruff.cryptotrackerapp.domain.repository.CoinMarketsCacheRepositoryImpl
import com.isaruff.cryptotrackerapp.domain.repository.CoinRepositoryImpl
import com.isaruff.cryptotrackerapp.domain.repository.TrackedCoinRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    @Binds
    fun bindTrackedCoinRepository(impl: TrackedCoinRepositoryImpl): TrackedCoinRepository

    @Binds
    fun bindCoinMarketsCacheRepository(impl: CoinMarketsCacheRepositoryImpl): CoinMarketsCacheRepository

}