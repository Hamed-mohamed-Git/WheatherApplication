package com.example.wheatherapplication.domain.usecase

import android.content.Context
import androidx.work.*
import com.example.wheatherapplication.ui.common.work_manger.WeatherAlertWorkManger
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class EnqueueWarningAlertWorkManger @Inject constructor(
    private val context: Context
) {
    operator fun invoke(interval: Long) =
        WorkManager.getInstance(context.applicationContext).enqueueUniquePeriodicWork(
            "alert",
            ExistingPeriodicWorkPolicy.KEEP,
            PeriodicWorkRequestBuilder<WeatherAlertWorkManger>(
                interval, TimeUnit.MINUTES
            ).setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(false)
                    .setRequiresCharging(false)
                    .build()
            ).setInitialDelay(3L, TimeUnit.MINUTES).build()
        )


}