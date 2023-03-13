package com.example.wheatherapplication.data.map

import com.example.wheatherapplication.constants.LengthUnit

object LengthUnitMapper {

     fun convertMileToKiloMeter(length: Double): Double =
        length * 1.60934

     fun convertKiloMeterToMile(length: Double): Double =
        length / 1.60934


    fun convertTo(currentLengthUnit: LengthUnit, length: Double?): Double =
        when (currentLengthUnit) {
            LengthUnit.MI -> {
                convertKiloMeterToMile(length ?: 0.0)
            }
            LengthUnit.KM -> {
                convertMileToKiloMeter(length ?: 0.0)
            }
        }

}