package com.example.wheatherapplication.ui.common.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NavGraphViewModel @Inject constructor() : ViewModel() {

    private val channel = Channel<DialogEventResult>()
    val eventResult = channel.receiveAsFlow()


    fun onEvent(event: DialogEvent) {
        viewModelScope.launch {
            when (event) {
                is DialogEvent.OnCancel -> {
                    channel.send(DialogEventResult.OnCLickCanceled)
                }
                is DialogEvent.OnDeleted -> {
                    channel.send(DialogEventResult.OnClickDeleted)
                }
            }
        }
    }
}