package com.camc.factory.data.model.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserService {
    @Headers("Content-Type: application/json-patch+json")
    @POST("api/Login/Login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
    companion object {
        fun create(baseUrl: String): UserService {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(UserService::class.java)
        }
    }
}

data class LoginRequest(
    val userName: String,
    val password: String,
    val code: String,
    val uuid: String,
    val loginIP: String
)

data class LoginResponse(
    val code: Int,
    val msg: String,
    val data: UserData
)

data class UserData(
    val img: String,
    val token: String,
    val userName: String,
    val deptName: String
)