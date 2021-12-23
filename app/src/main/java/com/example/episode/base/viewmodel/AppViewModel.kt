package com.example.episode.base.viewmodel

import androidx.lifecycle.LiveData
import com.example.episode.base.ext.NotNullLiveData

interface AppViewModel<S : AppViewState, E : AppSingleEvent> {
    val state: NotNullLiveData<S>
    val singleEvent: LiveData<AppEvent<E>>
}