package com.lbg.domain.model

sealed class ApiStatus<out T> {

    data object Loading : ApiStatus<Nothing>()
    data class Success<out T>(val value: T) : ApiStatus<T>()
    data class Error(val error: String? = null) :
        ApiStatus<Nothing>()

    data object NetworkError : ApiStatus<Nothing>()
}