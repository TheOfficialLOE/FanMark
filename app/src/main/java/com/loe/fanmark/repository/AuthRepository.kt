package com.loe.fanmark.repository

import com.loe.fanmark.network.AuthTransferObjects.AuthDataResponseObject
import com.loe.fanmark.network.AuthTransferObjects.LoginBodyObject
import com.loe.fanmark.network.AuthTransferObjects.SignUpObject
import com.loe.fanmark.network.ResultWrapper
import com.loe.fanmark.network.Service
import com.loe.fanmark.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher

class AuthRepository (private val service: Service) {

    suspend fun login(dispatcher: CoroutineDispatcher, body: LoginBodyObject): ResultWrapper<AuthDataResponseObject> {
        return safeApiCall(dispatcher) {service.retrofit.login(body)}
    }

    suspend fun signUp(dispatcher: CoroutineDispatcher, body: SignUpObject): ResultWrapper<AuthDataResponseObject> {
        return safeApiCall(dispatcher) {service.retrofit.signUp(body)}
    }

}