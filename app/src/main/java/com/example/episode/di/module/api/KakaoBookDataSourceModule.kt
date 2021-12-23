package com.example.episode.di.module.api

import com.example.episode.data.remote.repository.book.KakaoBookDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object KakaoBookDataSourceModule {

    @Provides
    @Singleton
    fun provideKakaoBookDataSource(
        retrofit: Retrofit
    ): KakaoBookDataSource = retrofit.create()


}

