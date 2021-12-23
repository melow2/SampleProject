package com.example.episode.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiInterceptor @Inject constructor() : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .apply {
                addHeader(AUTHORIZATION, BEARER + API_KEY)
            }.build()
        return chain.proceed(request)
    }

    companion object {
        const val AUTHORIZATION = "Authorization"
        const val BEARER = "KakaoAK "
        const val API_KEY = "4de907c5603758606a619f75c90ef4c1"
    }
}