package com.example.wheatherapplication.data.map

import android.location.Address
import com.example.wheatherapplication.domain.model.AddressData

object AddressMapper {
    fun convertToAddressData(address: Address): AddressData {
        val addresses = splitAddress(address.getAddressLine(0))
        return AddressData(
            addresses[0],
            addresses[if (addresses.size < 2) 0 else 1],
            addresses[if (addresses.size < 3) 0 else 2],
            address.adminArea,
            addresses[if (addresses.size < 5) 0 else 4],
        )
    }

    private fun splitAddress(addressInfo: String): List<String> =
        addressInfo.split(",")

}