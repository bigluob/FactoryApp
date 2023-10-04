package com.camc.factory.data.network.file

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("/api/DocLib/SingleFileUpload")
    suspend fun uploadFile(
        @Header("Authorization") token: String, // 添加 Token 请求头
        @Part file: MultipartBody.Part
    ): Response<UplaodResponseModel>
}

