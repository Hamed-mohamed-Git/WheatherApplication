package com.example.wheatherapplication.ui.common.navigation

sealed interface DialogEventResult {
    object OnCLickCanceled: DialogEventResult
    object OnClickDeleted: DialogEventResult
}
