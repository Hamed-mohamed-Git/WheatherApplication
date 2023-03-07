package com.example.wheatherapplication.domain.model

import com.example.wheatherapplication.constants.LengthUnit
import com.example.wheatherapplication.constants.Temperature

data class WeatherSetting(
    val temperatureUnit:Temperature,
    val lengthUnit:LengthUnit
)
