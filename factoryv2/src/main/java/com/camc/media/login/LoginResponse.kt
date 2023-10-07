package com.camc.factorymediav2.login

data class LoginResponse(
    val code: Int,
    val msg: String,
    val data: LoginResponseData,
    val success: Boolean,
    val errorMessage: String?
)

data class LoginResponseData(
    val img: String,
    val token: String,
    val userInfo: UserInfo
)

data class UserInfo(
    val username: String,
    val departmentName: String
)