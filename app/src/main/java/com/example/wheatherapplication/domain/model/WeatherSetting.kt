package com.example.wheatherapplication.domain.model

import com.example.wheatherapplication.constants.Language
import com.example.wheatherapplication.constants.LengthUnit
import com.example.wheatherapplication.constants.LocationType
import com.example.wheatherapplication.constants.Temperature


data class WeatherSetting(
    val notificationPermission:Boolean? = null,
    val locationType: LocationType? = null,
    var temperatureUnit:Temperature? = null,
    val lengthUnit:LengthUnit? = null,
    val language:Language? = null
)
