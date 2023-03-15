package com.example.wheatherapplication.ui.alert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.wheatherapplication.data.local.FavouriteWeatherInformation
import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.domain.model.WeatherSetting
import com.example.wheatherapplication.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertViewModel @Inject constructor(
    private val updateFavouriteWeatherInfo: UpdateFavouriteWeatherInfo,
    private val getFavouriteWeatherInfo: GetFavouriteWeatherInfo,
    private val getDataStoreLocationData: GetDataStoreLocationData,
    private val getDataStoreSettingData: GetDataStoreSettingData,
    private val setDataStoreSettingData: SetDataStoreSettingData,
    private val enqueueWarningAlertWorkManger: EnqueueWarningAlertWorkManger,
    private val cancelAlertWorkManger: CancelAlertWorkManger
) : ViewModel() {
    private val _settings = MutableStateFlow(WeatherSetting())
    val setting = _settings.asStateFlow()

    private val _weatherInfo = MutableStateFlow(FavouriteWeatherInformation("","",null,null))
    val weatherInfo = _weatherInfo.asStateFlow()

    init {
        viewModelScope.launch {
            getDataStoreSettingData().collect{
                _settings.emit(it)
            }
        }
        viewModelScope.launch {
            getDataStoreLocationData().collect{latLng ->
                getFavouriteWeatherInfo(latLng.latitude.toString()).collect{
                    _weatherInfo.emit(it)
                }
            }
        }
    }

    fun saveTime(currentTime: Long, time: Long) {
        viewModelScope.launch {
            getDataStoreLocationData().collect { latLng ->
                getFavouriteWeatherInfo(latLng.latitude.toString()).collect {
                   updateFavouriteInfo(
                       FavouriteWeatherInformation(
                           latLng.latitude.toString(),
                           latLng.longitude.toString(),
                           currentTime,
                           time
                       )
                   )
                }
            }
        }
    }

    fun enqueueNotification(){
        enqueueWarningAlertWorkManger(12)
    }
    fun cancelNotification(){
        cancelAlertWorkManger
    }

     fun setSettings(weatherSetting: WeatherSetting) =
        viewModelScope.launch(Dispatchers.IO) {
            setDataStoreSettingData(weatherSetting)
        }

    fun updateFavouriteInfo(favouriteWeatherInformation: FavouriteWeatherInformation){
        viewModelScope.launch {
            updateFavouriteWeatherInfo(
                favouriteWeatherInformation
            )
        }
    }

}