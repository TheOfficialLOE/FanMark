package com.loe.fanmark.network

import com.loe.fanmark.network.AuthTransferObjects.AuthDataResponseObject

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: AuthDataResponseObject? = null): ResultWrapper<Nothing>()
    object NetworkError: ResultWrapper<Nothing>()
}