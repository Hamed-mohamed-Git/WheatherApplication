package com.example.wheatherapplication.domain.model

import java.io.Serializable

data class WeatherLatLng(
    val lat:Double? = null,
    val lon:Double? = null
) : Serializable