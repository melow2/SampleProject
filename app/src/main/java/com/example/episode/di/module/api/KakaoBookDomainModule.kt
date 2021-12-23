package com.example.episode.di.module.api

import com.example.episode.data.remote.repository.book.KakaoBookRepositoryImpl
import com.example.episode.domain.repository.KakaoBookRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class KakaoBookDomainModule {
    @Binds
    abstract fun bindKakaoBookRepository(kakaoBookRepositoryImpl: KakaoBookRepositoryImpl): KakaoBookRepository
}