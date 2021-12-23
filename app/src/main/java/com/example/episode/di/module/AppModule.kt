package com.example.episode.di.module

import android.app.Application
import android.content.Context
import com.example.episode.data.ErrorMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun providePayContext(application: Application): Context = application

    @Provides
    @Singleton
    fun providePayErrorMapper(retrofit: Retrofit):ErrorMapper = ErrorMapper(retrofit)
}

