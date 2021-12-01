package com.example.fireapp.util

sealed class ResultWrapper<out T: Any>{
    data class Success<out T: Any>(val data: T) : ResultWrapper<T>()
    data class Failure(val throwable: Throwable) : ResultWrapper<Nothing>()
}