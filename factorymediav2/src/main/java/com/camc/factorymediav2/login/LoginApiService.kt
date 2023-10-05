package com.camc.factorymediav2

import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST("/api/Login/Login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}
