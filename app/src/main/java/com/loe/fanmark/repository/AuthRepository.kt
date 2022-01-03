package com.loe.fanmark.repository

import android.content.Context
import android.content.SharedPreferences
import com.loe.fanmark.network.AuthTransferObjects.AuthDataResponseObject
import com.loe.fanmark.network.AuthTransferObjects.LoginBodyObject
import com.loe.fanmark.network.AuthTransferObjects.SignUpObject
import com.loe.fanmark.network.ResultWrapper
import com.loe.fanmark.network.Service
import com.loe.fanmark.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Response

class AuthRepository (private val service: Service, private val context: Context) {

    private val sharedPref: SharedPreferences = context.getSharedPreferences("Auth", Context.MODE_PRIVATE)

    suspend fun login(dispatcher: CoroutineDispatcher, body: LoginBodyObject): ResultWrapper<Response<AuthDataResponseObject>> {
        return safeApiCall(dispatcher) {service.retrofit.login(body)}
    }

    suspend fun signUp(dispatcher: CoroutineDispatcher, body: SignUpObject): ResultWrapper<Response<AuthDataResponseObject>> {
        return safeApiCall(dispatcher) {service.retrofit.signUp(body)}
    }

    fun saveAuthInfo(token: String) {
        sharedPref.edit().putString("Token", token).apply()
    }

    fun getAuthInfo(){
        sharedPref.getString("Token", null)
    }

}