package com.example.episode.presentation.home.main

import com.example.episode.base.viewmodel.AppViewState
import com.example.episode.domain.EpisodeError
import com.example.episode.domain.entity.book.KakaoBookEntity

sealed class PayMainViewItem {
    data class Content(val book: KakaoBookEntity.Document) : PayMainViewItem()
    object Loading : PayMainViewItem()
    data class Error(val errorMessage: EpisodeError?) : PayMainViewItem()
}

data class PayMainViewState(
    val books: List<PayMainViewItem>,
    val state: State,
    val query: String?,
    val error: EpisodeError?,
    val isEnd: Boolean?,
    val updatePage: Int
): AppViewState {
    companion object {
        fun initial() = PayMainViewState(
            books = emptyList(),
            state = State.INIT,
            query = null,
            isEnd = false,
            error = null,
            updatePage = 0
        )
    }
    enum class State { INIT, DATA, LOADING, ERROR }
}