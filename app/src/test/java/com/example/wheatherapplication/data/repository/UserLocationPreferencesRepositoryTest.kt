package com.example.wheatherapplication.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wheatherapplication.data.local.repository.UserLocationPreferencesRepositoryImpl
import com.example.wheatherapplication.domain.repository.UserLocationPreferencesRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class UserLocationPreferencesRepositoryTest {
    private lateinit var testContext: Context
    private lateinit var testCoroutineDispatcher: TestDispatcher
    private lateinit var testCoroutineScope: TestScope
    private lateinit var testDataStore: DataStore<Preferences>
    private lateinit var repository: UserLocationPreferencesRepository

    @Before
    fun dataStoreInit() {
        testContext = ApplicationProvider.getApplicationContext()
        testCoroutineDispatcher = StandardTestDispatcher()
        testCoroutineScope = TestScope(testCoroutineDispatcher + Job())
        testDataStore = PreferenceDataStoreFactory.create(
            scope = testCoroutineScope,
            produceFile =
            { testContext.preferencesDataStoreFile("user") }
        )
        repository = UserLocationPreferencesRepositoryImpl(testDataStore)
    }


    @Test
    fun setAndGetDataStoreLocation_latLng_insertedLocation() = testCoroutineScope.runTest {

        //Given
        val location = LatLng(12.0, 20.0)

        //When
        repository.setDataStoreLocationData(location)

        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            repository.getDataStoreLocationData().collect{
                MatcherAssert.assertThat(it.latitude, Matchers.`is`(12.0))
                MatcherAssert.assertThat(it.longitude, Matchers.`is`(20.0))
            }
        }
    }
}