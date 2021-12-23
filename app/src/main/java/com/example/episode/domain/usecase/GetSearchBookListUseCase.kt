package com.example.episode.domain.usecase

import com.example.episode.domain.EpisodeResult
import com.example.episode.domain.entity.book.KakaoBookEntity
import com.example.episode.domain.repository.KakaoBookRepository
import javax.inject.Inject

class GetSearchBookListUseCase @Inject constructor(
    private val repository: KakaoBookRepository
) {
    suspend operator fun invoke(
        query: String,
        size: Int,
        page: Int
    ): EpisodeResult<KakaoBookEntity> {
        return repository.getSearchBookList(query, size, page)
    }
}