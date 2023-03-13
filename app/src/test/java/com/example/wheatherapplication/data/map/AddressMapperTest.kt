package com.example.wheatherapplication.data.map

import android.location.Address
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class AddressMapperTest {


    private var address: Address = Address(Locale(Locale.getDefault().toString())).run {
        countryName = "Egypt"
        countryCode = "2222"
        featureName = "Mit Ghamr - Zagazig Road"
        adminArea = "Dakahlia Governorate"
        subAdminArea = "Mit Ghamr"
        locality = "Madinet Mit Ghamr (Include Dagados)"
        latitude = 30.7155768
        longitude = 31.2725803
        setAddressLine(
            0,
            "Mit Ghamr - Zagazig Rd, Madinet Mit Ghamr (Include Dagados), Mit Ghamr, Dakahlia Governorate, Egypt"
        )
        this
    }


    @Test
    fun convertToAddressData_addressObject_addressDataObjet() {
        //given
        address

        //when
        val addressData = AddressMapper.convertToAddressData(address)

        //Then
        MatcherAssert.assertThat(addressData.country, Matchers.`is`("Egypt"))
        MatcherAssert.assertThat(addressData.cityDescription, Matchers.`is`("Mit Ghamr"))
        MatcherAssert.assertThat(addressData.city, Matchers.`is`("Madinet Mit Ghamr (Include Dagados)"))
        MatcherAssert.assertThat(addressData.road, Matchers.`is`("Mit Ghamr - Zagazig Road"))
    }
}