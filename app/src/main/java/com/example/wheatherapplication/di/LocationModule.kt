package com.example.wheatherapplication.di


import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.LocationServices
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
