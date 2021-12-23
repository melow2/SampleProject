package com.example.episode.data.remote.repository.book

import com.example.episode.data.remote.response.book.KakaoBookResponse
import com.example.episode.domain.entity.book.KakaoBookEntity

object KakaoBookMapper {

    fun responseToKakaoBookListModel(response: KakaoBookResponse): KakaoBookEntity {
        return KakaoBookEntity(
            documents = response.documents?.map {
                KakaoBookEntity.Document(
                    authors = it.authors,
                    contents = it.contents,
                    datetime = it.datetime,
                    isbn = it.isbn,
                    price = it.price,
                    publisher = it.publisher,
                    sale_price = it.sale_price,
                    status = it.status,
                    thumbnail = it.thumbnail,
                    title = it.title,
                    translators = it.translators,
                    url = it.url,
                    like = false
                )
            },
            meta = KakaoBookEntity.Meta(
                is_end = response.meta?.is_end,
                pageable_count = response.meta?.pageable_count,
                total_count = response.meta?.total_count
            )
        )
    }

}
