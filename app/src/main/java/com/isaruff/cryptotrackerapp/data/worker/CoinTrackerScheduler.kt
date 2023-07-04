package com.isaruff.cryptotrackerapp.data.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object CoinTrackerScheduler {
    fun refreshPeriodicWork(context: Context) {

        val myConstraints = Constraints.Builder()
            .setRequiresBatteryNotLow(false)
            .setRequiresStorageNotLow(false)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()


        val refreshCpnWork = PeriodicWorkRequest.Builder(CoinTrackerWorker::class.java,
            15, TimeUnit.MINUTES)
            .setInitialDelay(0, TimeUnit.MINUTES)
            .setConstraints(myConstraints)
            .addTag("CoinTrackerWorker")
            .build()


        WorkManager.getInstance(context).enqueueUniquePeriodicWork("CoinTrackerWorker",
            ExistingPeriodicWorkPolicy.UPDATE, refreshCpnWork)

    }
}