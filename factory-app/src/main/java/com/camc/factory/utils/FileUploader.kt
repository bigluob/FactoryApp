package com.camc.factory.utils

import android.content.Context
import com.camc.factory.data.network.file.ApiService
import com.camc.factory.data.network.file.FileUploadCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object FileUploader {
    private val scope = CoroutineScope(Dispatchers.IO)
private  val tokenDefault="eyJhbGciOiJub25lIiwidHlwIjoiQmVhcmVyIn0.eyJwcmltYXJ5c2lkIjoiMTU3NzU5NDcxNDE2ODgxOTcxMiIsImh0dHA6Ly9zY2hlb" +
        "WFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL3NpZCI6Ind1eGwxIiwidW5pcXVlX25hbWUiOiLlkLTmlrDlvZUiLCJodHRwOi8vc2NoZW1" +
        "hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3VzZXJkYXRhIjoie1wiVUlEXCI6XCIxNTc3NTk0NzE0MTY4ODE5NzEyXCIsXCJVc2VySUR" +
        "cIjpcInd1eGwxXCIsXCJVc2VyTmFtZVwiOlwi5ZC05paw5b2VXCIsXCJUZW5hbnRJRFwiOm51bGwsXCJUZW5hbnROYW1lXCI6bnVsbCxcIkRlcHRJRFwiOlwiMTU3NjIy" +
        "MjUyMTg4NTc4MjAxNlwiLFwiRGVwdE5hbWVcIjpcIuS_oemYs-WIhuWFrOWPuFwiLFwiR3JvdXBJRFwiOlwiMTU5ODg5NDI5ODAwNjc4MTk1MlwiLFwiR3JvdXBOYW1lXC" +
        "I6XCLnlJ_kuqfnrqHnkIbpg6hcIixcIkdyYWRlbGV2ZWxcIjpcIue7hOmVv1wiLFwiU2VjdXJpdHlOYW1lXCI6XCLlhazlvIBcIixcIlNlY3VyaXR5VmFsdWVcIjowLFwiUm9" +
        "sZUlkc1wiOm51bGwsXCJSb2xlc1wiOm51bGwsXCJQZXJtaXNzaW9uc1wiOm51bGx9IiwiQXVkaWVuY2UiOiJEaVhpbi5QRE0iLCJJc3N1ZXIiOiJEaVhpbi5QRE0iLCJuYmYiOjE" +
        "2OTU3MDIxNDMsImV4cCI6MTY5NTc0NTM0MywiaWF0IjoxNjk1NzAyMTQzLCJpc3MiOiJEaVhpbi5QRE0iLCJhdWQiOiJEaVhpbi5QRE0ifQ."
    fun uploadFiles(
        categoryName: String,
        files: List<File>,
        context: Context,
        fileUploadCallback: FileUploadCallback
    ) {
        val token = SharedPrefsManager.newInstance(context).getString("token", tokenDefault)
        scope.launch {
            try {
                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val originalRequest = chain.request()
                        val requestBuilder = originalRequest.newBuilder()
                            .header("Authorization", "Bearer $token") // 添加授权头部
                            .method(originalRequest.method, originalRequest.body)
                        val modifiedRequest = requestBuilder.build()
                        chain.proceed(modifiedRequest)
                    }
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("http://82.157.66.177:8888/")
                    .client(okHttpClient) // 使用带有授权头部的 OkHttpClient
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val apiService = retrofit.create(ApiService::class.java)

                for (file in files) {
                    val requestFile =
                        RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
                    val body = MultipartBody.Part.createFormData("formFile", file.name, requestFile)

                    val response = apiService.uploadFile(body)
                    if (response.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            fileUploadCallback.onFileUploadSuccess()
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        withContext(Dispatchers.Main) {
                            fileUploadCallback.onFileUploadError(errorBody ?: "Unknown error")
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    fileUploadCallback.onFileUploadError(e.message ?: "Unknown error")
                }
            }
        }
    }
}