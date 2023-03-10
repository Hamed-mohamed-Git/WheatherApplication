package com.example.wheatherapplication.ui.main_Activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.wheatherapplication.domain.model.WeatherSetting
import com.example.wheatherapplication.domain.usecase.GetDataStoreSettingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getDataStoreSettingData: GetDataStoreSettingData
):ViewModel() {
    private val _settings = MutableStateFlow(WeatherSetting())
    val setting = _settings.asStateFlow()

    fun getSetting(){
        viewModelScope.launch {
            getDataStoreSettingData().collect{
                _settings.emit(it)
            }
        }
    }
}