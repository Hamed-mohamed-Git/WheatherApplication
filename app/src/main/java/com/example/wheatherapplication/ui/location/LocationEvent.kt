package com.example.wheatherapplication.ui.location

import android.location.Location

sealed interface LocationEvent {
    object LocationPermissionRequest : LocationEvent
    class LocationInformationRequest(var location: Location?) : LocationEvent
}

