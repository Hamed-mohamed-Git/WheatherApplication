package com.example.wheatherapplication.data.map

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Test

class TemperatureMapperTest {

    @Test
    fun convertCelToKelvin_cleValue_kelvinValue(){
        //given
        val temp = 20.0

        //when
        val kelvin = TemperatureMapper.convertCelToKelvin(temp)

        //Then
        assertThat(kelvin, `is`(293.15))
    }

    @Test
    fun convertCelToFahrenheit_cleValue_FahrenheitValue(){
        //given
        val temp = 20.0

        //when
        val kelvin = TemperatureMapper.convertCelToFahrenheit(temp)

        //Then
        assertThat(kelvin, `is`(68.0))
    }

    @Test
    fun convertKelvinToFahrenheit_KelvinValue_FahrenheitValue(){
        //given
        val temp = 293.15

        //when
        val kelvin = TemperatureMapper.convertKelvinToFahrenheit(temp)

        //Then
        assertThat(kelvin, `is`(68.0))
    }

    @Test
    fun convertKelvinToCel_KelvinValue_CelValue(){
        //given
        val temp = 293.15

        //when
        val kelvin = TemperatureMapper.convertKelvinToCel(temp)

        //Then
        assertThat(kelvin, `is`(20.0))
    }

    @Test
    fun convertFahrenheitToKelvin_FahrenheitValue_KelvinValue(){
        //given
        val temp = 68.0

        //when
        val kelvin = TemperatureMapper.convertFahrenheitToKelvin(temp)

        //Then
        assertThat(kelvin, `is`(18.0))
    }

    @Test
    fun convertFahrenheitToCel_FahrenheitValue_CelValue(){
        //given
        val temp = 68.0

        //when
        val kelvin = TemperatureMapper.convertFahrenheitToKelvin(temp)

        //Then
        assertThat(kelvin, `is`(291.15))
    }
}