package com.example.wheatherapplication.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.wheatherapplication.data.local.AppDatabase
import com.example.wheatherapplication.data.local.FavouriteWeather
import kotlinx.coroutines.*
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@SmallTest
class FavouriteWeatherDataDaoTest {
    private lateinit var favouriteWeatherDataDao: FavouriteWeatherDataDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        favouriteWeatherDataDao = db.favouriteWeatherDataDao()
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
        val favouriteWeather = FavouriteWeather(
            "46.4929",
            "25.6457",
            ""
        )

        //When
        favouriteWeatherDataDao.insertFavouriteWeather(
            favouriteWeather
        )

        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            favouriteWeatherDataDao.getFavouriteWeathers().collect{
                assertThat(it.size, `is`(1))
            }
        }


    }

    @Test
    @Throws(Exception::class)
    fun deleteFavouriteWeather_favouriteWeather_weatherDeleted() = runTest {
        //Given
        val favouriteWeather = FavouriteWeather(
            "46.4929",
            "25.6457",
            ""
        )

        //When
        favouriteWeatherDataDao.insertFavouriteWeather(
            favouriteWeather
        )
        favouriteWeatherDataDao.deleteFavouriteWeather(
            favouriteWeather
        )

        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            favouriteWeatherDataDao.getFavouriteWeathers().collect{
                assertThat(it.size, `is`(0))
            }
        }


    }

    @Test
    @Throws(Exception::class)
    fun updateFavouriteWeather_favouriteWeather_weatherUpdated() = runTest {
        //Given
        val favouriteWeather = FavouriteWeather(
            "46.4929",
            "25.6457",
            ""
        )
        val favouriteWeatherUpdated = FavouriteWeather(
            "46.4929",
            "25.6457",
            "weather data"
        )

        //When
        favouriteWeatherDataDao.insertFavouriteWeather(
            favouriteWeather
        )
        favouriteWeatherDataDao.updateFavouriteWeather(
            favouriteWeatherUpdated
        )

        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            favouriteWeatherDataDao.getFavouriteWeather("46.4929").collect{
                assertThat(it.weather, `is`("weather data"))
            }
        }


    }

    @Test
    @Throws(Exception::class)
    fun getFavouriteWeatherById_favouriteWeather_weatherById() = runTest {
        //Given
        val favouriteWeather = FavouriteWeather(
            "46.4929",
            "25.6457",
            ""
        )

        //When
        favouriteWeatherDataDao.insertFavouriteWeather(
            favouriteWeather
        )

        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            favouriteWeatherDataDao.getFavouriteWeather("46.4929").collect{
                assertThat(it.lng, `is`("25.6457"))
            }
        }


    }

    @Test
    @Throws(Exception::class)
    fun updateLocationFavouriteWeather_favouriteWeather_weatherLocationUpdated() = runTest {
        //Given
        val favouriteWeather = FavouriteWeather(
            "46.4929",
            "25.6457",
            ""
        )

        //When
        favouriteWeatherDataDao.insertFavouriteWeather(
            favouriteWeather
        )

        favouriteWeatherDataDao.updateLocationFavouriteWeather("46.4929","1","1","weather app after updated")

        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            favouriteWeatherDataDao.getFavouriteWeather("1").collect{
                assertThat(it.lng, `is`("weather app after updated"))
            }
        }


    }

    @Test
    @Throws(Exception::class)
    fun checkFounded_favouriteWeather_weatherFounded() = runTest {
        //Given
        val favouriteWeather = FavouriteWeather(
            "46.4929",
            "25.6457",
            ""
        )

        //When
        favouriteWeatherDataDao.insertFavouriteWeather(
            favouriteWeather
        )



        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            favouriteWeatherDataDao.getFavouriteWeather("1").collect{
                assertThat(favouriteWeatherDataDao.checkFounded("46.4929"), `is`(1))
            }
        }


    }
}