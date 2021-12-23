package com.example.episode.domain.repository

import com.example.episode.domain.EpisodeResult
import com.example.episode.domain.entity.book.KakaoBookEntity

interface KakaoBookRepository {
    suspend fun getSearchBookList(query: String, size: Int, page: Int): EpisodeResult<KakaoBookEntity>
}