package com.isaruff.cryptotrackerapp.di

import com.isaruff.cryptotrackerapp.data.repository.CoinRepository
import com.isaruff.cryptotrackerapp.domain.repository.CoinRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

}