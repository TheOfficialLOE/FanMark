package com.loe.fanmark.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

object AuthTransferObjects {

    @JsonClass(generateAdapter = true)
    data class AuthDataResponseObject
        (
        override val success: Boolean,
        override val message: String,
        val data: String?
    ) : BaseDataTransferObject()

    @JsonClass(generateAdapter = true)
    data class LoginBodyObject
        (
        val MobileNumber: String? = null,
        val Username: String? = null,
        var Password: String
        )

    @JsonClass(generateAdapter = true)
    data class SignUpObject
        (
        val FullName: String,
        val MobileNumber: String,
        val Username: String,
        val Password: String
        )

}