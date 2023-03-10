package com.example.wheatherapplication.ui.common.navigation

sealed interface DialogEvent {
    object OnCancel: DialogEvent
    object OnDeleted: DialogEvent
}
