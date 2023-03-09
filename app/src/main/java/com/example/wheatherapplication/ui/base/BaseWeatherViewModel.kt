package com.example.wheatherapplication.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapplication.constants.Language
import com.example.wheatherapplication.constants.LengthUnit
import com.example.wheatherapplication.constants.Temperature
import com.example.wheatherapplication.data.OpenWeatherRepositoryImpl
import com.example.wheatherapplication.domain.model.AlertInformation
import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.domain.model.WeatherSetting
import com.example.wheatherapplication.domain.usecase.GetDataStoreSettingData
import com.example.wheatherapplication.domain.usecase.SetAlarm
import com.example.wheatherapplication.domain.usecase.SetDataStoreSettingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject


@HiltViewModel
class BaseWeatherViewModel @Inject constructor(
    private val openWeatherRepositoryImpl: OpenWeatherRepositoryImpl,
    private val getDataStoreSettingData: GetDataStoreSettingData,
    private val setDataStoreSettingData: SetDataStoreSettingData,
    private val setAlarm: SetAlarm
) : ViewModel() {
    private val _favouriteWeathers: MutableStateFlow<List<WeatherData>> =
        MutableStateFlow(emptyList())
    val favouriteWeathers = _favouriteWeathers.asStateFlow()

    private val _settings: MutableStateFlow<WeatherSetting> =
        MutableStateFlow(WeatherSetting())
    val settings = _settings.asStateFlow()

    fun getFavouriteWeathers() {
        viewModelScope.launch {
            openWeatherRepositoryImpl.getFavouriteWeathers().collect {
                _favouriteWeathers.emit(it)
            }
        }
    }

    fun getSettings() {
        viewModelScope.launch {
            getDataStoreSettingData().collect {
                _settings.emit(it)
            }

        }
    }

    fun setSettings() {
        viewModelScope.launch {
            setDataStoreSettingData(
                WeatherSetting(
                    Temperature.KELVIN.toString(),
                    LengthUnit.KILOMETER.toString(),
                    Language.ARABIC.toString()
                )
            )
        }
    }

    fun setAlarmTime(calendar: Calendar) {
        setAlarm(
            calendar.timeInMillis, AlertInformation(
                "NWS Philadelphia - Mount Holly (New Jersey, Delaware, Southeastern Pennsylvania)",
                null,
                "SMALL CRAFT ADVISORY REMAINS IN EFFECT FROM 5 PM THIS\\nAFTERNOON TO 3 AM EST FRIDAY...\\n* WHAT...North winds 15 to 20 kt with gusts up to 25 kt and seas\\n3 to 5 ft expected.\\n* WHERE...Coastal waters from Little Egg Inlet to Great Egg\\nInlet NJ out 20 nm, Coastal waters from Great Egg Inlet to\\nCape May NJ out 20 nm and Coastal waters from Manasquan Inlet\\nto Little Egg Inlet NJ out 20 nm.\\n* WHEN...From 5 PM this afternoon to 3 AM EST Friday.\\n* IMPACTS...Conditions will be hazardous to small craft."
            ),0
        )
    }
}