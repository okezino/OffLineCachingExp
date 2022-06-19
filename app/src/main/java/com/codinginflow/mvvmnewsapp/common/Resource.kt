package com.codinginflow.mvvmnewsapp.common

sealed class Resource<T>(
    var data: T? = null,
    var error: Throwable? = null
) {

    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T?) : Resource<T>(data)
    class Error<T>(error: Throwable, data: T? = null) : Resource<T>(data, error)
}