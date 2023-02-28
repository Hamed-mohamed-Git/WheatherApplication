package com.example.wheatherapplication.domain.usecase

import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class GetGeoCoderLocation @Inject constructor(
    private val geocoder: Geocoder,
) {

    operator fun invoke(latLng: LatLng): MutableList<Address>? =
        geocoder.getFromLocation(latLng.latitude,
            latLng.longitude,
            1)
}