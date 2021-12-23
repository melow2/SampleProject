package com.example.episode.domain

sealed class EpisodeError : Throwable()

object NetworkError : EpisodeError()

sealed class LocalStorageError : EpisodeError() {
    object DeleteFileError : LocalStorageError()
    object SaveFileError : LocalStorageError()
    data class DatabaseError(override val cause: Throwable? = null) : LocalStorageError()
}

data class ServerError(
    override val message: String?,
    val statusCode: Int?,
) : EpisodeError()

data class UnexpectedError(
    override val message: String,
    override val cause: Throwable?,
) : EpisodeError()

fun EpisodeError.getMessage(): String {
    return when (this) {
        NetworkError -> "Network error"
        is ServerError -> "$message"
        is UnexpectedError -> "An unexpected error occurred"
        LocalStorageError.DeleteFileError -> "Error when deleting file"
        LocalStorageError.SaveFileError -> "Error when saving file"
        is LocalStorageError.DatabaseError -> "Database error"
    }
}