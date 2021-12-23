package com.example.episode.domain

import com.example.episode.base.ext.Either

typealias EpisodeResult<T> = Either<EpisodeError, T>