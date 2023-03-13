package com.example.wheatherapplication.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wheatherapplication.data.local.FavouriteWeatherInformation
import com.example.wheatherapplication.data.local.dao.FavouriteWeatherInformationDao
import com.example.wheatherapplication.data.local.dao.fake.FakeFavouriteWeatherInformationDao
import com.example.wheatherapplication.domain.repository.FavouriteWeatherInfoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class FavouriteWeatherInfoRepositoryTest {
    private lateinit var favouriteWeatherInformationDao: FavouriteWeatherInformationDao
    private lateinit var favouriteWeatherInfoRepositoryImpl: FavouriteWeatherInfoRepository


    @Before
    fun createRepo() {
        favouriteWeatherInformationDao = FakeFavouriteWeatherInformationDao()
        favouriteWeatherInfoRepositoryImpl =
            FavouriteWeatherInfoRepositoryImpl(favouriteWeatherInformationDao)
    }


    @Test
    fun insertFavouriteWeatherInfo_favouriteWeatherInfo_insertedWeatherInfo() = runTest {
        //Given
        val favouriteWeather = FavouriteWeatherInformation(
            "46.4929",
            "25.6457",
            10000L,
            20000L
        )
        //When
        repeat(5) {
            favouriteWeatherInfoRepositoryImpl.insertFavouriteWeatherInfo(favouriteWeather)
        }

        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            favouriteWeatherInfoRepositoryImpl.getFavoriteWeathersInfo().collect {
                assertThat(it.size, `is`(5))
            }
        }


    }

    @Test
    fun updateLocationFavouriteWeatherInfo_favouriteWeatherInfo_updatedWeatherInfo() = runTest {
        //Given
        val favouriteWeather = FavouriteWeatherInformation(
            "46.4929",
            "25.6457",
            10000L,
            20000L
        )

        //When
        favouriteWeatherInfoRepositoryImpl.insertFavouriteWeatherInfo(favouriteWeather)
        favouriteWeatherInfoRepositoryImpl.updateFavouriteWeatherInfo(
            FavouriteWeatherInformation(
                "200000",
                "200000",
                40000L,
                240000L
            )
        )

        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            favouriteWeatherInfoRepositoryImpl.getFavoriteWeatherInfo("200000").collect {
                assertThat(it.alertEnd, `is`(240000L))
            }
        }


    }

    @Test
    fun deleteFavouriteWeather_favouriteWeatherInfo_deletedWeatherInfo() = runTest {
        //Given
        val favouriteWeather = FavouriteWeatherInformation(
            "46.4929",
            "25.6457",
            10000L,
            20000L
        )

        //When
        favouriteWeatherInfoRepositoryImpl.insertFavouriteWeatherInfo(favouriteWeather)
        favouriteWeatherInfoRepositoryImpl.deleteFavouriteWeatherInfo(
            favouriteWeather
        )

        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            favouriteWeatherInfoRepositoryImpl.getFavoriteWeathersInfo().collect {
                assertThat(it.size, `is`(0))
            }
        }


    }

    @Test
    fun getFavouriteWeathersInfo_favouriteWeatherInfo_allWeatherInfo() = runTest {
        //Given
        val favouriteWeather = FavouriteWeatherInformation(
            "46.4929",
            "25.6457",
            10000L,
            20000L
        )

        //When
        repeat(5){
            favouriteWeatherInfoRepositoryImpl.insertFavouriteWeatherInfo(favouriteWeather)
        }


        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            favouriteWeatherInfoRepositoryImpl.getFavoriteWeathersInfo().collect {
                repeat(it.size){index ->
                    assertThat(it[index].alertEnd, `is`(20000L))
                }
            }
        }


    }


}