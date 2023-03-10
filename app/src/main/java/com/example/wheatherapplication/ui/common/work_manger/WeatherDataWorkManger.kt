package com.example.wheatherapplication.ui.common.work_manger

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.wheatherapplication.constants.Constants
import com.example.wheatherapplication.constants.LengthUnit
import com.example.wheatherapplication.constants.Temperature
import com.example.wheatherapplication.domain.usecase.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class WeatherDataWorkManger @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val getFavouriteWeathersInfo: GetFavouriteWeathersInfo,
    private val getWeatherData: GetWeatherData,
    private val updateFavouriteWeather: UpdateFavouriteWeather,
    private val checkFavouriteWeatherDataFounded: CheckFavouriteWeatherDataFounded,
    private val saveFavouriteWeatherData: SaveFavouriteWeatherData
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        getFavouriteWeathersInfo().collect {
            repeat(it.size) { index ->
                with(it[index]) {
                    getWeatherData(
                        this.lat.toDouble(),
                        this.lng.toDouble(),
                        Constants.API_UNIT_METRIC,
                        Temperature.CELSIUS,
                        Temperature.CELSIUS,
                        LengthUnit.KM
                    ).collect { weatherData ->
                        if (checkFavouriteWeatherDataFounded(
                                weatherData.lat.toString()
                            ) != null
                        ) {
                            updateFavouriteWeather(weatherData)
                            return@collect
                        }
                        else {
                            saveFavouriteWeatherData(
                                weatherData, Temperature.CELSIUS,
                                Temperature.CELSIUS,
                                LengthUnit.KM
                            )
                            return@collect
                        }
                    }
                }
            }
        }
        return Result.success()
    }
}