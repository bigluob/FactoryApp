package com.camc.factory.data.network.login

import retrofit2.http.Body
import retrofit2.http.POST

// 登录请求的数据类
data class LoginRequest(
    val userName: String,
    val password: String,
    val code: String,
    val uuid: String,
    val loginIP: String
)

// 登录响应的数据类
data class LoginResponse(
    val code: Int,
    val msg: String,
    val data: LoginData
)

data class LoginData(
    val img: String,
    val token: String,
    val userName: String,
    val deptName: String
)

// 登录API接口
interface LoginApi {
    @POST("/api/Login/Login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}
