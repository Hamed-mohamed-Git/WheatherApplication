package com.example.wheatherapplication.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.wheatherapplication.data.local.AppDatabase
import com.example.wheatherapplication.data.local.FavouriteWeather
import com.example.wheatherapplication.data.local.FavouriteWeatherInformation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@SmallTest
class FavouriteWeatherInformationDaoTest {
    private lateinit var favouriteWeatherInfoDao: FavouriteWeatherInformationDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        favouriteWeatherInfoDao = db.favouriteWeatherInfoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertFavouriteWeather_favouriteWeather_weatherAdded() = runTest {
        //Given
        val favouriteWeather = FavouriteWeatherInformation(
            "46.4929",
            "25.6457",
            10000L,
            20000L
        )

        //When
        favouriteWeatherInfoDao.insertFavouriteWeatherInfo(
            favouriteWeather
        )

        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            favouriteWeatherInfoDao.getFavouriteWeathersInfo().collect {
                MatcherAssert.assertThat(it.size, Matchers.`is`(1))
            }
        }


    }

    @Test
    @Throws(Exception::class)
    fun deleteFavouriteWeather_favouriteWeather_weatherDeleted() = runTest {
        //Given
        val favouriteWeather = FavouriteWeatherInformation(
            "46.4929",
            "25.6457",
            10000L,
            20000L
        )

        //When
        favouriteWeatherInfoDao.insertFavouriteWeatherInfo(
            favouriteWeather
        )
        favouriteWeatherInfoDao.deleteFavouriteWeather(
            favouriteWeather
        )

        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            favouriteWeatherInfoDao.getFavouriteWeathersInfo().collect {
                MatcherAssert.assertThat(it.size, Matchers.`is`(0))
            }
        }


    }

    @Test
    @Throws(Exception::class)
    fun updateFavouriteWeather_favouriteWeather_weatherUpdated() = runTest {
        //Given
        //Given
        val favouriteWeather = FavouriteWeatherInformation(
            "46.4929",
            "25.6457",
            10000L,
            20000L
        )
        val favouriteWeatherUpdated = FavouriteWeatherInformation(
            "46.4929",
            "25.6457",
            220000L,
            230000L
        )

        //When
        favouriteWeatherInfoDao.insertFavouriteWeatherInfo(
            favouriteWeather
        )
        favouriteWeatherInfoDao.updateFavouriteWeather(
            favouriteWeatherUpdated
        )

        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            favouriteWeatherInfoDao.getFavouriteWeatherInfo("46.4929").collect {
                MatcherAssert.assertThat(it.alertStart, Matchers.`is`(220000L))
            }
        }


    }

    @Test
    @Throws(Exception::class)
    fun getFavouriteWeatherById_favouriteWeather_weatherById() = runTest {
        //Given
        val favouriteWeather = FavouriteWeatherInformation(
            "46.4929",
            "25.6457",
            10000L,
            20000L
        )

        //When
        favouriteWeatherInfoDao.insertFavouriteWeatherInfo(
            favouriteWeather
        )

        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            favouriteWeatherInfoDao.getFavouriteWeatherInfo("46.4929").collect {
                MatcherAssert.assertThat(it.lng, Matchers.`is`("25.6457"))
            }
        }


    }

    @Test
    @Throws(Exception::class)
    fun updateLocationFavouriteWeather_favouriteWeather_weatherLocationUpdated() = runTest {
        //Given
        val favouriteWeather = FavouriteWeatherInformation(
            "46.4929",
            "25.6457",
            10000L,
            20000L
        )

        //When
        favouriteWeatherInfoDao.insertFavouriteWeatherInfo(
            favouriteWeather
        )

        favouriteWeatherInfoDao.updateLocationFavouriteWeatherInfo(
            "46.4929", "1", "1", 0, 0
        )

        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            favouriteWeatherInfoDao.getFavouriteWeatherInfo("1").collect {
                MatcherAssert.assertThat(it.lng, Matchers.`is`("1"))
            }
        }


    }

}