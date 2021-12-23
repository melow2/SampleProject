package com.example.episode.di.module

import com.example.episode.data.remote.ApiInterceptor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

@InstallIn(SingletonComponent::class)
@Module
abstract class InterceptorModule {
    @Binds
    @IntoSet
    abstract fun bindApiInterceptor(apiInterceptor: ApiInterceptor):Interceptor

    @Binds
    @IntoSet
    abstract fun bindHttpLoggingInterceptor(httpLoggingInterceptor: HttpLoggingInterceptor):Interceptor

}