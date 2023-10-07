package com.camc.common.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginUtils {
    private val BASE_URL = "http://82.157.66.177:8888/"

    private val loginApi: LoginApi

    init {
        val okHttpClient = OkHttpClient.Builder()
            // 设置其他OkHttp配置，如超时时间等
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        loginApi = retrofit.create(LoginApi::class.java)
    }

    fun login(userName: String, password: String, code: String, uuid: String, loginIP: String) {
        val request = LoginRequest(userName, password, code, uuid, loginIP)

        val call = loginApi.login(request)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                val loginResponse = response.body()
                if (loginResponse != null && loginResponse.code == 200) {
                    // 登录成功，处理成功响应
                    val data = loginResponse.data
                    val token = data.token
                } else {
                    // 登录失败，处理错误响应
                    // 可以根据实际情况处理不同的错误码和错误信息
                }
            } else {
                // 处理错误响应
                // 可以根据实际情况处理不同的HTTP状态码
            }
        } catch (e: Exception) {
            // 处理异常情况
            e.printStackTrace()
        }
    }
}