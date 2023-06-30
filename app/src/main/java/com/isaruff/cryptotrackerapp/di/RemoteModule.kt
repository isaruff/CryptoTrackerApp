package com.isaruff.cryptotrackerapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.isaruff.cryptotrackerapp.common.Constants.BASE_URL
import com.isaruff.cryptotrackerapp.data.remote.service.CoinGeckoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, httpClient: OkHttpClient): Retrofit {
        val retrofitInstance = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .client(httpClient)


        return retrofitInstance.build()

    }

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val httpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
            .callTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)

        return httpBuilder.build()
    }

    @Provides
    @Singleton
    fun provideGsonConverter(): Gson = GsonBuilder().create()


    @Provides
    @Singleton
    fun provideCoinApi(retrofit: Retrofit): CoinGeckoService =
        retrofit.create(CoinGeckoService::class.java)

}