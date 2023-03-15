package com.example.wheatherapplication.ui.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.wheatherapplication.constants.*
import com.example.wheatherapplication.data.local.FavouriteWeatherInformation
import com.example.wheatherapplication.data.map.FavouriteWeatherDataMapper
import com.example.wheatherapplication.domain.model.AddressData
import com.example.wheatherapplication.domain.model.WeatherSetting
import com.example.wheatherapplication.domain.usecase.*
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val getCurrentLocation: GetCurrentLocation,
    private val getGeoCoderLocation: GetGeoCoderLocation,
    private val setDataStoreLocationData: SetDataStoreLocationData,
    private val getDataStoreSettingData: GetDataStoreSettingData,
    private val getDataStoreLocationData: GetDataStoreLocationData,
    private val getFavouriteWeatherInfo: GetFavouriteWeatherInfo,
    private val saveFavouriteWeatherInfo: SaveFavouriteWeatherInfo,
    private val getWeatherData: GetWeatherData,
    private val updateCurrentLocationFavouriteWeather: UpdateCurrentLocationFavouriteWeather,
    private val updateCurrentFavouriteWeatherInfo: UpdateCurrentLocationWeatherInfo,
    private val enqueueWeatherWorkManger: EnqueueWeatherWorkManger,
    private val setDataStoreSettingData: SetDataStoreSettingData
) : ViewModel() {
    val address = MutableLiveData<AddressData?>()
    val latLng = MutableLiveData<LatLng?>()

    private val _locationEventChannel = Channel<LocationEvent>()
    val locationEvent = _locationEventChannel.receiveAsFlow().asLiveData()

    fun getGpsLocation(checkPermission: Boolean) {
        viewModelScope.launch {
            if (checkPermission) {
                getCurrentLocation().collect {
                    latLng.postValue(LatLng(it.latitude, it.longitude))
                }
            } else
                _locationEventChannel.send(LocationEvent.LocationPermissionRequest)

        }
    }

    fun convertLatLngToAddress(latLng: LatLng): Unit =
        address.postValue(getGeoCoderLocation(latLng))

    fun prepareUserLocation(latLng: LatLng?, locationType: LocationType, isPrepared: Boolean) {
        viewModelScope.launch {
            setSettings(locationType, isPrepared)
            with(latLng ?: LatLng(0.0, 0.0)) {
                if (isPrepared) {
                    getDataStoreLocationData().collect {
                        getFavouriteWeatherInfo(it.latitude.toString()).collect { favourite ->
                            updateCurrentFavouriteWeatherInfo(
                                it.latitude.toString(),
                                FavouriteWeatherInformation(
                                    latitude.toString(),
                                    longitude.toString(),
                                    favourite.alertStart ?: 0L,
                                    favourite.alertEnd ?: 0L
                                )
                            )
                        }
                        getWeatherData(
                            latitude, longitude,
                            Constants.API_UNIT_METRIC,
                            Temperature.CELSIUS,
                            Temperature.CELSIUS,
                            LengthUnit.KM

                        ).collect{weather ->
                            updateCurrentLocationFavouriteWeather(
                                it.latitude.toString(),
                                FavouriteWeatherDataMapper.convertToFavouriteWeather(weather)
                            )
                        }

                    }
                } else {
                    saveFavouriteWeatherInfo(
                        FavouriteWeatherInformation(
                            latitude.toString(),
                            longitude.toString(),
                            0L,
                            0L
                        )
                    )
                }
                enqueueWeatherWorkManger(15L)
                setDataStoreLocationData(this)
            }
        }
    }


    private fun setSettings(locationType: LocationType, isPrepared: Boolean) {
        viewModelScope.launch {
            if (isPrepared) {
                getDataStoreSettingData().collect {
                    setDataStoreSettingData(
                        WeatherSetting(
                            severeWeather = it.severeWeather,
                            durationTime = it.durationTime,
                            notificationPermission = it.notificationPermission,
                            locationType = locationType,
                            temperatureUnit = it.temperatureUnit,
                            lengthUnit = it.lengthUnit,
                            language = it.language
                        )
                    )
                }
            } else {
                setDataStoreSettingData(
                    WeatherSetting(
                        severeWeather = false,
                        durationTime = false,
                        notificationPermission = false,
                        locationType = locationType,
                        temperatureUnit = Temperature.CELSIUS,
                        lengthUnit = LengthUnit.KM,
                        language = Language.ENGLISH
                    )
                )
            }

        }
    }


    //                getWeatherData(
//                    latitude,
//                    longitude,
//                    Constants.API_UNIT_METRIC,
//                    Temperature.CELSIUS,
//                    Temperature.CELSIUS,
//                    LengthUnit.KM
//                ).collect {
//                    saveFavouriteWeatherData(
//                        it,
//                        Temperature.CELSIUS,
//                        Temperature.CELSIUS,
//                        LengthUnit.KM
//                    )
//                }

}