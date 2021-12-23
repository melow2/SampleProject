package com.example.episode.base.view

import com.example.episode.base.viewmodel.AppSingleEvent
import com.example.episode.base.viewmodel.AppViewState

interface BaseView<S:AppViewState,E:AppSingleEvent> {
    fun render(viewState:S)
    fun handleEvent(event:E)
}