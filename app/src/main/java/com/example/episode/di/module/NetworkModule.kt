package com.example.episode.di.module

import android.content.Context
import com.example.episode.BuildConfig
import com.example.episode.di.DaggerSet
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    private const val KAKAO_BASE_URL = "kakao_base_url"
    private const val BASE_URL = "https://dapi.kakao.com/v3/"

    @Provides
    @Named(KAKAO_BASE_URL)
    fun provideBaseUrlString(): String = BASE_URL

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // Use newBuilder() to customize so that thread-pool and connection-pool same are used
    @Provides
    fun provideOkHttpClientBuilder(
        okHttpClient: Lazy<OkHttpClient>
    ): OkHttpClient.Builder = okHttpClient.get().newBuilder()

    @Provides
    @Singleton
    fun provideBaseOkHttpClient(
        interceptors: DaggerSet<Interceptor>,
        cache: Cache
    ): OkHttpClient {
        // check(Looper.myLooper() != Looper.getMainLooper()) { "HTTP client initialized on main thread." }
        val builder = OkHttpClient.Builder()
        builder.interceptors().addAll(interceptors)
        builder.cache(cache)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideCache(context: Context): Cache {
        // check(Looper.myLooper() != Looper.getMainLooper()) { "Cache initialized on main thread." }
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val cacheDir = context.cacheDir
        return Cache(cacheDir, cacheSize.toLong())
    }


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: Lazy<OkHttpClient>,
        moshi: Moshi,
        @Named(KAKAO_BASE_URL) baseUrl: String
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .callFactory { okHttpClient.get().newCall(it) }
        .build()

}

@PublishedApi
internal inline fun Retrofit.Builder.callFactory(
    crossinline body: (Request) -> Call
) = callFactory(object : Call.Factory {
    override fun newCall(request: Request): Call = body(request)
})
