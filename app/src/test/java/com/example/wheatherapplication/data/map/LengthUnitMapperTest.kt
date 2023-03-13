package com.example.wheatherapplication.data.map

import com.example.wheatherapplication.constants.LengthUnit
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test

class LengthUnitMapperTest {

    @Test
    fun convertMileToKiloMeter_mileValue_kiloValue(){
        //given
        val mile = 20.0

        //when
        val kiloMeter = LengthUnitMapper.convertMileToKiloMeter(mile)

        //Then
        MatcherAssert.assertThat(kiloMeter, Matchers.`is`(32.1868))
    }

    @Test
    fun convertKiloMeterToMile_kiloMeterValue_mileValue(){
        //given
        val kiloMeter = 32.1868

        //when
        val mile = LengthUnitMapper.convertKiloMeterToMile(kiloMeter)

        //Then
        MatcherAssert.assertThat(mile, Matchers.`is`(20.0))
    }

    @Test
    fun convertTo_currentDegree_anotherDegree(){
        //given
        val kiloMeter = 32.1868
        val mile = 20.0

        //when
        val firstLength = LengthUnitMapper.convertTo(LengthUnit.MI,kiloMeter)
        val secondLength = LengthUnitMapper.convertTo(LengthUnit.KM,mile)

        //Then
        MatcherAssert.assertThat(firstLength, Matchers.`is`(20.0))
        MatcherAssert.assertThat(secondLength, Matchers.`is`(32.1868))
    }
}