package com.example.wheatherapplication.domain.usecase

import android.content.Context
import androidx.work.*
import com.example.wheatherapplication.ui.common.work_manger.WeatherDataWorkManger
import java.math.BigInteger
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class EnqueueWeatherWorkManger @Inject constructor(
    private val context: Context
) {
    operator fun invoke(interval: Long) =
        WorkManager.getInstance(context.applicationContext).enqueueUniquePeriodicWork(
            "app",
            ExistingPeriodicWorkPolicy.KEEP,
            PeriodicWorkRequestBuilder<WeatherDataWorkManger>(
                interval, TimeUnit.MINUTES
            ).setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(false)
                    .setRequiresCharging(false)
                    .build()
            ).build()
        )


}