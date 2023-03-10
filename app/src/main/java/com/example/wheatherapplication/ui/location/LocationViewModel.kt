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
                getCurrentLocation().apply {
                    latLng.postValue(LatLng(this.latitude, this.longitude))
                }
            } else
                _locationEventChannel.send(LocationEvent.LocationPermissionRequest)

        }
    }

    fun convertLatLngToAddress(latLng: LatLng): Unit =
        address.postValue(getGeoCoderLocation(latLng))

    fun saveLatLng(latLng: LatLng?, locationType: LocationType) {
        viewModelScope.launch {
            setDataStoreSettingData(
                WeatherSetting(
                    locationType,
                    Temperature.CELSIUS,
                    LengthUnit.KM,
                    Language.ENGLISH
                )
            )
            with(latLng ?: LatLng(0.0, 0.0)) {
                saveFavouriteWeatherInfo(
                    FavouriteWeatherInformation(
                        latitude.toString(),
                        longitude.toString(),
                        String().format(Constants.STANDARD_WORKER_ID, latitude, longitude),
                        0L,
                        0L
                    )
                )
                enqueueWeatherWorkManger(
                    12L,
                    30,
                    String().format(Constants.STANDARD_WORKER_ID, latitude, longitude)
                )
                setDataStoreLocationData(this)

            }
        }
    }


}