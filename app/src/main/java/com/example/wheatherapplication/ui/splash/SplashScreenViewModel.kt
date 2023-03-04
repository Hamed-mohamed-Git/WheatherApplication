package com.example.wheatherapplication.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapplication.domain.usecase.GetDataStoreLocationData
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val getDataStoreLocationData: GetDataStoreLocationData
):ViewModel() {
    private val _dataStoreLocationData:MutableStateFlow<DataStoreLocationState> = MutableStateFlow(DataStoreLocationState.LocationNull)
    val dataStoreLocationData = _dataStoreLocationData

    fun getDataStoreLocationDataFound(){
        viewModelScope.launch {
            getDataStoreLocationData().collect{
                if (checkLocationFounded(it))
                    _dataStoreLocationData.emit(DataStoreLocationState.LocationNotFounded)
                else
                    _dataStoreLocationData.emit(DataStoreLocationState.LocationFounded)
            }
        }
    }

    private fun checkLocationFounded(latLng: LatLng) = latLng.latitude == 0.0 && latLng.longitude == 0.0
}