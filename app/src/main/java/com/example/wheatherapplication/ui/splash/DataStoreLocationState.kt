package com.example.wheatherapplication.ui.splash

sealed interface DataStoreLocationState{
    object LocationFounded:DataStoreLocationState
    object LocationNotFounded:DataStoreLocationState
    object LocationNull:DataStoreLocationState
}