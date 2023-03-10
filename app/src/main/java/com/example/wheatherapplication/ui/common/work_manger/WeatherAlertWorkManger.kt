package com.example.wheatherapplication.ui.common.work_manger

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.wheatherapplication.constants.Constants
import com.example.wheatherapplication.constants.LengthUnit
import com.example.wheatherapplication.constants.Temperature
import com.example.wheatherapplication.domain.model.AlertInformation
import com.example.wheatherapplication.domain.usecase.GetDataStoreLocationData
import com.example.wheatherapplication.domain.usecase.GetFavouriteWeathersInfo
import com.example.wheatherapplication.domain.usecase.GetWeatherData
import com.example.wheatherapplication.domain.usecase.SetAlarm
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.Calendar


@HiltWorker
class WeatherAlertWorkManger @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val getFavouriteWeathersInfo: GetFavouriteWeathersInfo,
    private val getDataStoreLocationData: GetDataStoreLocationData,
    private val getWeatherData: GetWeatherData,
    private val setAlarm: SetAlarm
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        getFavouriteWeathersInfo().collect {
            Calendar.getInstance().time.time.let {date ->
                //if (it[0].alertStart!! >= date && it[0].alertEnd!! <= date)
                    getDataStoreLocationData().collect {
                        getWeatherData(
                            it.latitude, it.longitude,
                            Constants.API_UNIT_METRIC,
                            Temperature.CELSIUS,
                            Temperature.CELSIUS,
                            LengthUnit.KM
                        ).collect { weatherData ->
                            weatherData.alerts?.let { alerts ->
                                if (alerts.isNotEmpty()) {
                                    alerts[0]?.let { alert ->
                                        setAlarm(
                                            60000L, AlertInformation(
                                                alert.senderName,
                                                alert.event,
                                                alert.description
                                            ), 0
                                        )

                                    }
                                }

                            }
                        }
                    }
            }

        }
        return Result.success()
    }
}