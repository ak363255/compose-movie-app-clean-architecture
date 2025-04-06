package com.example.domain.models

sealed class Result<out T> {
    data class Success<D>(val data: D) : Result<D>()
    object Loading: Result<Nothing>()
    data class Error(val error : String): Result<Nothing>()
}