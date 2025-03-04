package com.lbg.domain.model

sealed class Resource<T>{
    class Error<T>(val message:String): Resource<T>()
    class Success<T>(val data:T?): Resource<T>()
}
