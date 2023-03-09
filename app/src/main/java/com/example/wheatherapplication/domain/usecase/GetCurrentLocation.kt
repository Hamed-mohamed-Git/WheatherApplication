package com.example.wheatherapplication.domain.usecase

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetCurrentLocation @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) {

    @SuppressLint("MissingPermission")
    suspend operator fun invoke():Location =
        fusedLocationProviderClient
            .getCurrentLocation(CurrentLocationRequest.Builder()
                .build(),null)
            .await()




}