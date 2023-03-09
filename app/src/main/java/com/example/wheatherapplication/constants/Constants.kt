package com.example.wheatherapplication.constants

import com.example.wheatherapplication.R

object Constants {
    const val APP_API_KEY = "c81f23105bfe2dd63e96c86dfa6c0952"
    const val API_BASE_URL = "https://api.openweathermap.org/data/"
    const val API_ICON_URL = "https://openweathermap.org/img/wn/"
    const val PLACES_API_KEY = "AIzaSyAbuHdF1hB_ddtCtgEZ7iE3cDqXk4zpVJU"
    const val DEGREE_UNIT_DATASTORE_KEY = "degreeUnit"
    const val LENGTH_UNIT_DATASTORE_KEY= "lengthUnit"
    const val LANGUAGE_DATASTORE_KEY= "language"
    const val LOCATION_TYPE_DATASTORE_KEY= "location"
    const val API_UNIT_METRIC = "metric"
    const val API_UNIT_IMPERIAL = "imperial"
    const val DATE_HOUR_PATTERN = "hh a"
    const val DATE_DAY_PATTERN = "yyyy-MM-dd"
    const val FULL_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss"
    const val BROADCAST_RECEIVER_INTENT_KEY = "intent_cast"
    const val BROADCAST_RECEIVER_CHANNEL_ID = "ALERT"
}

enum class Temperature{
    KELVIN,
    FAHRENHEIT,
    CELSIUS
}

enum class LengthUnit{
    MILE,
    KILOMETER
}

enum class Language{
    ENGLISH,
    ARABIC
}