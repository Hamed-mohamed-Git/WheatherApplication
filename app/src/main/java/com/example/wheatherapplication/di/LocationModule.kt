package com.example.wheatherapplication.di


import android.content.Context
import android.location.Geocoder
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.work.impl.model.Preference
import com.example.wheatherapplication.data.UserLocationPreferencesRepositoryImpl
import com.example.wheatherapplication.domain.repository.UserLocationPreferencesRepository
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import java.util.*


@Module
@InstallIn(ViewModelComponent::class)
object LocationModule {

    @Provides
    fun provideLocation(context: Context) = LocationServices
        .getFusedLocationProviderClient(context)

    @Provides
    fun provideGeoCoder(context: Context) = Geocoder(context, Locale.getDefault())

}
