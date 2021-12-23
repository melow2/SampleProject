package com.example.episode.presentation.home.main

import com.example.episode.base.viewmodel.AppSingleEvent


sealed class PayMainSingleEvent: AppSingleEvent {
    data class MessageEvent(val message:String?):PayMainSingleEvent()
}
