package com.example.wheatherapplication.ui.alert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.wheatherapplication.data.local.FavouriteWeatherInformation
import com.example.wheatherapplication.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertViewModel @Inject constructor(
    private val updateFavouriteWeatherInfo: UpdateFavouriteWeatherInfo,
    private val getFavouriteWeatherInfo: GetFavouriteWeatherInfo,
    private val getDataStoreLocationData: GetDataStoreLocationData,
    private val enqueueWarningAlertWorkManger: EnqueueWarningAlertWorkManger,
    private val cancelAlertWorkManger: CancelAlertWorkManger
) : ViewModel() {

    fun saveTime(currentTime: Long, time: Long) {
        viewModelScope.launch {
            getDataStoreLocationData().collect { latLng ->
                getFavouriteWeatherInfo(latLng.latitude.toString()).collect {
                    updateFavouriteWeatherInfo(
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
}