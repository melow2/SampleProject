package com.example.episode.data.remote.repository.book

import com.example.episode.base.ext.Right
import com.example.episode.data.ErrorMapper
import com.example.episode.domain.EpisodeResult
import com.example.episode.domain.entity.book.KakaoBookEntity
import com.example.episode.domain.repository.KakaoBookRepository
import javax.inject.Inject

class KakaoBookRepositoryImpl @Inject constructor(
    private val errorMapper: ErrorMapper,
    private val kakaoBookDataSource: KakaoBookDataSource
) : KakaoBookRepository {

    override suspend fun getSearchBookList(
        query: String,
        size: Int,
        page: Int
    ): EpisodeResult<KakaoBookEntity> {
        return runCatching {
            kakaoBookDataSource.getSearchBookList(
                query = query,
                size = size,
                page = page
            ).let(KakaoBookMapper::responseToKakaoBookListModel)
        }.fold({
            Right(it)
        }, {
            errorMapper.mapAsLeft(it)
        })
    }
}