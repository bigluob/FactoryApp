package com.camc.media.login

data class LoginRequest(
    val userName: String,
    val password: String,
    val code: String,
    val uuid: String,
    val loginIP: String
)
