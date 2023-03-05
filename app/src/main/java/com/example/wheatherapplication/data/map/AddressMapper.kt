package com.example.wheatherapplication.data.map

import android.location.Address
import com.example.wheatherapplication.domain.model.AddressData

object AddressMapper {
    fun convertToAddressData(address: Address): AddressData {
        val addresses = splitAddress(address.getAddressLine(0))
        return AddressData(
            if (address.featureName != null) address.featureName else addresses[0],
            if (address.subAdminArea != null) address.subAdminArea else (addresses[if (addresses.size < 2) 0 else 1]),
            if (address.locality != null) address.locality else (addresses[if (addresses.size < 3) 0 else 2]),
            if (address.adminArea != null) address.adminArea else ( addresses[if (addresses.size < 4) 0 else 3]),
            if (address.countryName != null) address.countryName else (addresses[if (addresses.size < 5) 0 else 4]),
        )
    }

    private fun splitAddress(addressInfo: String): List<String> =
        addressInfo.split(",")

}