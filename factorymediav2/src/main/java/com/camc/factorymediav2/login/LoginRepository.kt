package com.camc.factorymediav2

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(private val loginApiService: LoginApiService) {
    // 定义登录请求函数，使用 Retrofit 发起登录请求
}