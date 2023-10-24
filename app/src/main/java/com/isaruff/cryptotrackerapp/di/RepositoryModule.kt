package com.isaruff.cryptotrackerapp.di

import com.isaruff.cryptotrackerapp.data.local.repository.CoinMarketsCacheRepositoryImpl
import com.isaruff.cryptotrackerapp.data.local.repository.TrackedCoinRepositoryImpl
import com.isaruff.cryptotrackerapp.data.remote.repository.CoinRepositoryImpl
import com.isaruff.cryptotrackerapp.domain.repository.CoinMarketsCacheRepository
import com.isaruff.cryptotrackerapp.domain.repository.CoinRepository
import com.isaruff.cryptotrackerapp.domain.repository.TrackedCoinRepository
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