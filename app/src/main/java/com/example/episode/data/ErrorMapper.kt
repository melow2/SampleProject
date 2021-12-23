package com.example.episode.data

import android.database.sqlite.SQLiteException
import com.example.episode.base.ext.left
import com.example.episode.domain.*
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ErrorMapper(private val retrofit: Retrofit) {

    fun map(throwable: Throwable): EpisodeError {
        return when (throwable) {
            is EpisodeError -> throwable

            is SQLiteException -> {
                LocalStorageError.DatabaseError(throwable)
            }

            is IOException -> {
                when (throwable) {
                    is UnknownHostException -> NetworkError
                    is SocketTimeoutException -> NetworkError
                    else -> UnexpectedError(
                        cause = throwable,
                        message = "Unknown IOException $this"
                    )
                }
            }

            is HttpException -> {
                ErrorResponseParser
                    .getError(
                        throwable.response() ?: return ServerError("Response is null", -1),
                        retrofit
                    )?.let { ServerError(message = it.message, statusCode = it.errorType) }
                    ?: ServerError("", -1)
            }

            else -> {
                UnexpectedError(
                    cause = throwable,
                    message = "Unknown throwable $throwable"
                )
            }
        }
    }


    fun <T> mapAsLeft(throwable: Throwable): EpisodeResult<T> = map(throwable).left()
}