package com.example.wheatherapplication.data.map

import com.example.wheatherapplication.constants.LengthUnit

object LengthUnitMapper {

    private fun convertMileToKiloMeter(length: Double): Double =
        length * 1.60934

    private fun convertKiloMeterToMile(length: Double): Double =
        length / 1.60934


    fun convertTo(currentLengthUnit: LengthUnit, length: Double?): Double =
        when (currentLengthUnit) {
            LengthUnit.MILE -> {
                convertKiloMeterToMile(length ?: 0.0)
            }
            LengthUnit.KILOMETER -> {
                convertMileToKiloMeter(length ?: 0.0)
            }
        }

}