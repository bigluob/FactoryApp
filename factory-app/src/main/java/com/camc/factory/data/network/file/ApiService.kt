package com.camc.factory.data.network.file

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("/api/DocLib/SingleFileUpload")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part
    ): Response<UplaodResponseModel>
}