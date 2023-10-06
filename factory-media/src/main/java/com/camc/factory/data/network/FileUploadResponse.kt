package com.camc.factory.data.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

// 文件上传响应的数据类
data class FileUploadResponse(
    val isSuccess: Boolean,
    val returnMsg: String,
    val completeFilePath: String
)

// 文件上传API接口
interface FileUploadApi {
    @Multipart
    @POST("/api/Common/SingleFileUpload")
    suspend fun uploadFile(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): retrofit2.Response<FileUploadResponse>
}