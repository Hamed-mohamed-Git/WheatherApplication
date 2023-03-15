package com.example.wheatherapplication.ui.common.work_manger

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.wheatherapplication.constants.Constants
import com.example.wheatherapplication.constants.LengthUnit
import com.example.wheatherapplication.constants.LocationType
import com.example.wheatherapplication.constants.Temperature
import com.example.wheatherapplication.domain.model.AlertInformation
import com.example.wheatherapplication.domain.usecase.*
import com.google.android.gms.maps.model.LatLng
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
            System.currentTimeMillis().let { date ->
                it[0].alertEnd?.let {
                    if (date <= it) {
                        getDataStoreLocationData().collect {latLng ->
                            getWeatherData(
                                latLng.latitude, latLng.longitude,
                                Constants.API_UNIT_METRIC,
                                Temperature.CELSIUS,
                                Temperature.CELSIUS,
                                LengthUnit.KM
                            ).collect { weatherData ->
                                if (weatherData.alerts != null){
                                    weatherData.alerts.let { alerts ->
                                        if (alerts.isNotEmpty()) {
                                            alerts[0]?.let { alert ->
                                                setAlarm(
                                                    20000L, AlertInformation(
                                                        "Emergency Alert",
                                                        alert.senderName,
                                                        alert.event,
                                                        alert.description
                                                    ), 0
                                                )

                                            }
                                        }

                                    }
                                }else{
                                    weatherData.current?.weather?.let {weatherItem ->
                                        setAlarm(
                                            20000L, AlertInformation(
                                                "weather condition",
                                                weatherItem.description,
                                                weatherItem.main,
                                                weatherItem.description
                                            ), 0
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        WorkManager.getInstance(this.applicationContext)
                            .cancelUniqueWork(Constants.WORK_MANGER_ALERT_TAG_NAME)
                    }
                }

            }

        }
        return Result.success()
    }
}