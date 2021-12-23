package com.example.episode.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.episode.base.ext.NotNullLiveData
import com.example.episode.base.ext.NotNullMutableLiveData
import timber.log.Timber

abstract class BaseViewModel<S : AppViewState, E : AppSingleEvent>(val initialState: S) :
    ViewModel(), AppViewModel<S, E> {

    private val stateD = NotNullMutableLiveData(initialState)
    override val state: NotNullLiveData<S> get() = stateD

    private val singleEventD = MutableLiveData<AppEvent<E>>()
    override val singleEvent: LiveData<AppEvent<E>> get() = singleEventD

    init {
        Timber.d("$this ::init")
    }

    fun setNewState(state: S) {
        if (state != stateD.value) {
            stateD.value = state
        }
    }

    fun sendEvent(event: E) {
        singleEventD.postValue(AppEvent(event))
    }
}