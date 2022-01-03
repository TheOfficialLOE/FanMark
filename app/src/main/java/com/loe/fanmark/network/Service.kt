package com.loe.fanmark.network

import com.loe.fanmark.network.AuthTransferObjects.AuthDataResponseObject
import com.loe.fanmark.network.AuthTransferObjects.LoginBodyObject
import com.loe.fanmark.network.AuthTransferObjects.SignUpObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface DemoMarketingAppNetworkService {
    @Headers("Content-Type: application/json")
    @POST("Auth/Login")
    suspend fun login(@Body body: LoginBodyObject): Response<AuthDataResponseObject>

    @Headers("Content-Type: application/json")
    @POST("Auth/SignUp")
    suspend fun signUp(@Body body: SignUpObject): Response<AuthDataResponseObject>
}

object Service {

    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl("https://demo-marketing-app-max-shady.fandogh.cloud/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()


    val retrofit: DemoMarketingAppNetworkService by lazy {
        retrofitBuilder.create(DemoMarketingAppNetworkService::class.java)
    }

}