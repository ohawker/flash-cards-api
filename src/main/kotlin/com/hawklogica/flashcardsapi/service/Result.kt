package com.hawklogica.flashcardsapi.service

sealed class Result<out T, out E> {
    data class Ok<out T>(val value: T) : Result<T, Nothing>()
    data class Err<out E>(val error: Any, val code: Int) : Result<Nothing, E>()
}
