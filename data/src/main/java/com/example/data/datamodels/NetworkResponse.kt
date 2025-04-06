package com.example.data.datamodels

import com.example.domain.models.Result

sealed class NetworkResponse<out T> {
    data class Success<D>(val data: D) : NetworkResponse<D>()
    data class Error(val error : String): NetworkResponse<Nothing>()
}