package com.example.episode.data.remote.repository.book

import com.example.episode.data.remote.response.book.KakaoBookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoBookDataSource {

    @GET("/v3/search/book")
    suspend fun getSearchBookList(
        @Query("query") query: String,
        @Query("size") size: Int,
        @Query("page") page: Int,
    ): KakaoBookResponse

}