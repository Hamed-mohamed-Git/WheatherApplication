package com.example.wheatherapplication.domain.usecase

import android.location.Address
import android.location.Geocoder
import com.example.wheatherapplication.data.map.AddressMapper
import com.example.wheatherapplication.domain.model.AddressData
import com.google.android.gms.maps.model.LatLng
import java.util.*
import javax.inject.Inject

class GetGeoCoderLocation @Inject constructor(
    private val geocoder: Geocoder,
) {

    operator fun invoke(latLng: LatLng): AddressData  =
        AddressMapper.convertToAddressData(geocoder.getFromLocation(latLng.latitude,
            latLng.longitude,
            5)?.get(0) ?: Address(Locale.getDefault())
        )

}