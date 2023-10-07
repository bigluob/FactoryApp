package com.camc.common.http

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
    val data: LoginData
)

data class LoginData(
    val img: String,
    val token: String,
    val userName: String,
    val deptName: String
)