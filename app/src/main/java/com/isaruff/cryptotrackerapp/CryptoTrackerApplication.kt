package com.isaruff.cryptotrackerapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CryptoTrackerApplication: Application() {

}

//    @Inject
//    lateinit var workerFactory: CoinTrackerWorkerFactory
//    override fun getWorkManagerConfiguration(): Configuration {
//        return Configuration.Builder()
//            .setMinimumLoggingLevel(Log.DEBUG)
//            .setWorkerFactory(workerFactory)
//            .build()
//    }
//}
//class CoinTrackerWorkerFactory @Inject constructor(
//    private val api: CoinGeckoService,
//    private val dao: TrackedCoinDao
//): WorkerFactory(){
//    override fun createWorker(
//        appContext: Context,
//        workerClassName: String,
//        workerParameters: WorkerParameters
//    ): ListenableWorker = CoinTrackerWorker(remoteApi = api,
//        trackedCoinDao = dao, context = appContext, params = workerParameters)
//
//}