package com.example.wheatherapplication.ui.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.wheatherapplication.constants.*
import com.example.wheatherapplication.data.local.FavouriteWeatherInformation
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
    private val saveFavouriteWeatherInfo: SaveFavouriteWeatherInfo,
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

    fun prepareUserLocation(latLng: LatLng?, locationType: LocationType) {
        viewModelScope.launch {
            setSettings(locationType)
            with(latLng ?: LatLng(0.0, 0.0)) {
                saveFavouriteWeatherInfo(
                    FavouriteWeatherInformation(
                        latitude.toString(),
                        longitude.toString(),
                        0L,
                        0L
                    )
                )
                enqueueWeatherWorkManger(15L)
                setDataStoreLocationData(this)
            }
        }
    }


    private fun setSettings(locationType: LocationType) {
        viewModelScope.launch {
            setDataStoreSettingData(
                WeatherSetting(
                    false,
                    locationType,
                    Temperature.CELSIUS,
                    LengthUnit.KM,
                    Language.ENGLISH
                )
            )
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