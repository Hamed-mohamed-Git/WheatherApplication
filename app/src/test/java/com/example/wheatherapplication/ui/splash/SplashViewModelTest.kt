package com.example.wheatherapplication.ui.splash

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wheatherapplication.data.repository.fake.FakeUserLocationPreferencesRepository
import com.example.wheatherapplication.domain.repository.UserLocationPreferencesRepository
import com.example.wheatherapplication.domain.usecase.GetDataStoreLocationData
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
class SplashViewModelTest {

    private lateinit var repository: UserLocationPreferencesRepository
    private lateinit var getDataStoreLocationData: GetDataStoreLocationData
    private lateinit var splashScreenViewModel: SplashScreenViewModel

    private lateinit var testContext: Context
    private lateinit var testCoroutineDispatcher: TestDispatcher
    private lateinit var testCoroutineScope: TestScope
    private lateinit var testDataStore: DataStore<Preferences>

    private fun dataStoreInit(){
        testContext = ApplicationProvider.getApplicationContext()
        testCoroutineDispatcher  = StandardTestDispatcher()
        testCoroutineScope = TestScope(testCoroutineDispatcher + Job())
        testDataStore = PreferenceDataStoreFactory.create(
            scope = testCoroutineScope,
            produceFile =
            { testContext.preferencesDataStoreFile("user") }
        )
    }

    @Before
    fun splashInit(){
        dataStoreInit()
        repository = FakeUserLocationPreferencesRepository(testDataStore)
        getDataStoreLocationData = GetDataStoreLocationData(repository)
        splashScreenViewModel = SplashScreenViewModel(getDataStoreLocationData)
    }

    @Test
    fun collectLocation_checkLocationFounded() = runTest {
        //Given
        val location = LatLng(20.0,20.0)


        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            //When
            splashScreenViewModel.getDataStoreLocationDataFound()
            repository.setDataStoreLocationData(location)

        }

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            //then
            splashScreenViewModel.dataStoreLocationData.collect {
                MatcherAssert.assertThat(it, Matchers.`is`(DataStoreLocationState.LocationFounded))
            }
        }

    }
}