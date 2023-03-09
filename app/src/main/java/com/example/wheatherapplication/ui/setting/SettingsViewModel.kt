package com.example.wheatherapplication.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapplication.domain.model.WeatherSetting
import com.example.wheatherapplication.domain.usecase.GetDataStoreSettingData
import com.example.wheatherapplication.domain.usecase.SetDataStoreSettingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getDataStoreSettingData: GetDataStoreSettingData,
    private val setDataStoreSettingData: SetDataStoreSettingData
) : ViewModel() {
    private val _settings = MutableStateFlow(WeatherSetting())
    val setting = _settings.asStateFlow()


    fun getSettings() {
        viewModelScope.launch {
            getDataStoreSettingData().collect {
                _settings.emit(it)
            }
        }
    }

    fun setSettings(weatherSetting: WeatherSetting){
        viewModelScope.launch {
            setDataStoreSettingData(weatherSetting)
        }
    }
}