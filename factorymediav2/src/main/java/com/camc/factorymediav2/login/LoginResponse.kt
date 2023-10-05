package com.camc.factorymediav2

import android.service.autofill.UserData

data class LoginResponse(
    val code: Int,
    val msg: String,
    val data: UserData // 适配实际响应数据结构
)
