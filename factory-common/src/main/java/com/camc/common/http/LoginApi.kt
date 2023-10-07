package com.camc.common.http

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginApi {
    @Headers(
        "accept: */*",
        "Content-Type: application/json-patch+json"
    )
    @POST("api/Login/Login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}