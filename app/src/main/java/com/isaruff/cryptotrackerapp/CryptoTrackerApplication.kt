package com.isaruff.cryptotrackerapp

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.isaruff.cryptotrackerapp.data.local.dao.TrackedCoinDao
import com.isaruff.cryptotrackerapp.data.remote.service.CoinGeckoService
import com.isaruff.cryptotrackerapp.data.worker.CoinTrackerWorker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CryptoTrackerApplication: Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: CoinTrackerWorkerFactory
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

}



class CoinTrackerWorkerFactory @Inject constructor(
    private val api: CoinGeckoService,
    private val dao: TrackedCoinDao
): WorkerFactory(){
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = CoinTrackerWorker(remoteApi = api,
        trackedCoinDao = dao, context = appContext, params = workerParameters)

}