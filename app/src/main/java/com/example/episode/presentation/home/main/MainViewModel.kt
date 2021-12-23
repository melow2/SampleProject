package com.example.episode.presentation.home.main

import androidx.lifecycle.viewModelScope
import com.example.episode.base.ext.fold
import com.example.episode.base.viewmodel.BaseViewModel
import com.example.episode.domain.usecase.GetSearchBookListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class PayMainViewModel @Inject constructor(
    private val getSearchBookListUseCase: GetSearchBookListUseCase
) : BaseViewModel<PayMainViewState, PayMainSingleEvent>(PayMainViewState.initial()) {

    fun searchBookList(query: String) {
        viewModelScope.launch {
            getSearchBookListUseCase(query, 20, 1).fold({
                sendEvent(PayMainSingleEvent.MessageEvent(it.message))
            }, {
                val newBooks = it.documents?.map { PayMainViewItem.Content(it) } ?: emptyList()
                val nextState = state.value.copy(
                    books = newBooks,
                    query = query,
                    state = PayMainViewState.State.DATA,
                    isEnd = it.meta?.is_end,
                    updatePage = 1
                )
                setNewState(nextState)
            })
        }
    }

//    fun loadNextPage() {
//        val prevState = state.value
//        viewModelScope.launch {
//            getSearchBookListUseCase(prevState.query ?: "", 20, prevState.updatePage + 1)
//                .fold({
//                    sendEvent(PayMainSingleEvent.MessageEvent(it.message))
//                }, {
//                    val newBooks = it.documents?.map { PayMainViewItem.Content(it) } ?: emptyList()
//                    val nextState = state.value.copy(
//                        books = prevState.books.filterNot(PayMainViewItem::isLoadingOrError)
//                            .plus(newBooks),
//                        state = PayMainViewState.State.DATA,
//                        isEnd = it.meta?.is_end,
//                        updatePage = prevState.updatePage + 1
//                    )
//                    setNewState(nextState)
//                })
//        }
//    }


}


fun PayMainViewItem.isLoadingOrError(): Boolean {
    return this is PayMainViewItem.Loading || this is PayMainViewItem.Error
}
