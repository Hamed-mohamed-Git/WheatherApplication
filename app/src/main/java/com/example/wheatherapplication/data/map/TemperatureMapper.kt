package com.example.wheatherapplication.data.map

import com.example.wheatherapplication.constants.Temperature

private const val FAHRENHEIT_NUM = 273.15
//private const val FAHRENHEIT:Double = ((9 / 5).toDouble())
//private const val CELSIUS:Double = (5 / 9).toDouble()

object TemperatureMapper {


    private fun convertCelToKelvin(degree: Double) =
        degree + FAHRENHEIT_NUM

    private fun convertCelToFahrenheit(degree: Double) =
        (degree * 1.8) + 32

    private fun convertKelvinToFahrenheit(degree: Double) =
        (degree - FAHRENHEIT_NUM) * 1.8 + 32

    private fun convertKelvinToCel(degree: Double) =
        degree - FAHRENHEIT_NUM

    private fun convertFahrenheitToCel(degree: Double) =
        (degree - 32) * 0.5

    private fun convertFahrenheitToKelvin(degree: Double) =
        convertFahrenheitToCel(degree) + FAHRENHEIT_NUM


    fun convertDegreeTo(
        currentDegree: Temperature,
        degreeType: Temperature,
        degree: Double?
    ): Double =
        when (currentDegree) {
            Temperature.CELSIUS -> {
                when (degreeType) {
                    Temperature.KELVIN -> convertCelToKelvin(degree ?: 0.0)
                    Temperature.FAHRENHEIT -> convertCelToFahrenheit(degree ?: 0.0)
                    else -> degree ?: 0.0
                }
            }
            Temperature.FAHRENHEIT -> {
                when (degreeType) {
                    Temperature.KELVIN -> convertFahrenheitToKelvin(degree ?: 0.0)
                    Temperature.CELSIUS -> convertFahrenheitToCel(degree ?: 0.0)
                    else -> degree ?: 0.0
                }
            }
            Temperature.KELVIN -> {
                when (degreeType) {
                    Temperature.CELSIUS -> convertKelvinToCel(degree ?: 0.0)
                    Temperature.FAHRENHEIT -> convertKelvinToFahrenheit(degree ?: 0.0)
                    else -> degree ?: 0.0
                }
            }
        }

}