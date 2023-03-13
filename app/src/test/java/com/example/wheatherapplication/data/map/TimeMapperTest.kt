package com.example.wheatherapplication.data.map

import org.hamcrest.MatcherAssert.*
import org.hamcrest.CoreMatchers.`is`
import org.junit.Test
import kotlin.time.Duration.Companion.days


class TimeMapperTest {

    @Test
    fun convertTimeStamp_timeStamp_date() {
        assertThat(TimeMapper.convertTimeStampToDate(1678685280L), `is`("2023-03-13"))
        assertThat(TimeMapper.convertTimeStampToFullDate(1678685280L), `is`("2023-03-13 07:28:00"))
        assertThat(TimeMapper.convertTimeStampToHour(1678685280L), `is`("07 AM"))
        assertThat(TimeMapper.convertTimeStampToLocalDate(1678685280L).dayOfMonth, `is`(13))
        assertThat(TimeMapper.convertTimeStampToLocalDate(1678685280L).monthValue, `is`(3))
        assertThat(TimeMapper.convertTimeStampToLocalDate(1678685280L).year, `is`(2023))
        assertThat(TimeMapper.convertTimeStampToLocalDate(1678685280L).dayOfWeek.value, `is`(1))
    }
}