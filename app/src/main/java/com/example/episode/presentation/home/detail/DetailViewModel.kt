package com.example.episode.presentation.home.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.episode.base.ext.NotNullMutableLiveData
import com.example.episode.base.viewmodel.AppSingleEvent
import com.example.episode.base.viewmodel.AppViewState
import com.example.episode.base.viewmodel.BaseViewModel
import com.example.episode.domain.EpisodeError
import com.example.episode.domain.entity.book.KakaoBookEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PayDetailViewState(
    val books: KakaoBookEntity.Document?,
    val state: State,
    val like:Boolean = false,
    val error: EpisodeError?
) : AppViewState {
    companion object {
        fun initial(books: KakaoBookEntity.Document?) = PayDetailViewState(
            books = books,
            state = State.INIT,
            like = books?.like?:false,
            error = null
        )
    }
    enum class State { INIT, DATA, LOADING, ERROR }
}

sealed class PayDetailSingleEvent: AppSingleEvent {
    data class MessageEvent(val message:String):PayDetailSingleEvent()
}


@HiltViewModel
class PayDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<PayDetailViewState, PayDetailSingleEvent>(PayDetailViewState.initial(null)) {

    private val stateS = NotNullMutableLiveData(initialState)

    fun toggleLike() = viewModelScope.launch{
            val prevState = stateS.value
            val likeStatus = prevState.like
            val newState = stateS.value.copy(
                books = prevState.books?.apply { like=!like },
                like = !likeStatus
            )
            setNewState(newState)
        }

}


