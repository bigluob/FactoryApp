package com.camc.factory.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var baseUrl = "http://82.157.66.177:8888" // 默认服务器地址和端口
    private var okHttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null

    fun setBaseUrl(newBaseUrl: String) {
        baseUrl = newBaseUrl

        // 重新创建 Retrofit 实例
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient ?: OkHttpClient.Builder().build())
            .build()
    }

    fun getBaseUrl(): String {
        return baseUrl
    }

    private fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient ?: OkHttpClient.Builder().build())
                .build()
        }
        return retrofit!!
    }

    val loginApi: LoginApi by lazy { getRetrofit().create(LoginApi::class.java) }
    val fileUploadApi: FileUploadApi by lazy { getRetrofit().create(FileUploadApi::class.java) }
}
