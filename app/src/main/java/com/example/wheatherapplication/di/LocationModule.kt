package com.example.wheatherapplication.di


import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import java.util.*


@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    fun provideLocation(context: Context):FusedLocationProviderClient = LocationServices
        .getFusedLocationProviderClient(context)

    @Provides
    fun provideGeoCoder(context: Context):Geocoder = Geocoder(context, Locale.getDefault())

}
