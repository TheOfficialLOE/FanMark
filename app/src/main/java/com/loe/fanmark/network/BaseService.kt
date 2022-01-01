package com.loe.fanmark.network

import com.loe.fanmark.network.AuthTransferObjects.AuthDataResponseObject
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        }catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val errResponse = convertErrorBody(throwable)
                    ResultWrapper.GenericError(code, errResponse)
                }
                else -> ResultWrapper.GenericError(null, null)
            }
        }
    }
}

private fun convertErrorBody (throwable: HttpException): AuthDataResponseObject? {
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            val moshi = Moshi.Builder().build().adapter(AuthDataResponseObject::class.java)
            moshi.fromJson(it)
        }
    }catch (ex: Exception) {
        null
    }
}