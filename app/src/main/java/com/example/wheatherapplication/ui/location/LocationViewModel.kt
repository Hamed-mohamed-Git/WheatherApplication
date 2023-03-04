package com.example.wheatherapplication.ui.location

import android.location.Address
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.wheatherapplication.domain.model.AddressData
import com.example.wheatherapplication.domain.usecase.GetCurrentLocation
import com.example.wheatherapplication.domain.usecase.GetDataStoreLocationData
import com.example.wheatherapplication.domain.usecase.GetGeoCoderLocation
import com.example.wheatherapplication.domain.usecase.SetDataStoreLocationData
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val getCurrentLocation: GetCurrentLocation,
    private val getGeoCoderLocation: GetGeoCoderLocation,
    private val setDataStoreLocationData: SetDataStoreLocationData
):ViewModel() {
    val address = MutableLiveData<AddressData?>()
    val latLng = MutableLiveData<LatLng?>()

    private val _locationEventChannel = Channel<LocationEvent>()
    val locationEvent = _locationEventChannel.receiveAsFlow().asLiveData()

    fun getGpsLocation(checkPermission:Boolean){
        viewModelScope.launch {
            if (checkPermission) {
                getCurrentLocation()?.apply {
                    latLng.postValue(LatLng(this.latitude,this.longitude))
                }
            }
            else
                _locationEventChannel.send(LocationEvent.LocationPermissionRequest)

        }
    }

    fun convertLatLngToAddress(latLng: LatLng): Unit = address.postValue(getGeoCoderLocation(latLng))

    fun saveLatLng(latLng: LatLng?){
        viewModelScope.launch{
            setDataStoreLocationData(latLng ?: LatLng(0.0,0.0))
        }
    }


}