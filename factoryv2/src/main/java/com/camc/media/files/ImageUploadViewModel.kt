package com.camc.media.files

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camc.factory.data.network.FileUploadApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ImageUploadViewModel @Inject constructor(
    // 注入 ApiService
    private val fileUploadApi: FileUploadApi
) : ViewModel() {
    // 选择的图像文件
    private val _selectedImageFile = MutableLiveData<Uri?>()
    val selectedImageFile: MutableLiveData<Uri?>
        get() = _selectedImageFile

    // 上传进度
    private val _uploadProgress = MutableStateFlow(0.0f)
    val uploadProgress: StateFlow<Float> = _uploadProgress


    // 上传结果
    private val _uploadResult = MutableLiveData<ResultStatus>()
    val uploadResult: LiveData<ResultStatus>
        get() = _uploadResult

    // 选择图像文件
    fun selectImageFile(uri: Uri) {
        _selectedImageFile.value = uri
    }

    // 上传图像
    fun uploadImage(selectedImageFile: Set<File>, token: String) {
        // 执行图像上传逻辑，包括网络请求、进度更新等
        // 更新上传进度和上传结果
        _uploadProgress.value = 0.0f // 设置初始进度
        _uploadResult.value = ResultStatus.InProgress // 设置上传中状态

        // 执行上传操作，可能需要使用协程来处理
        // 在上传过程中，更新 _uploadProgress
        // 在上传完成或失败时，更新 _uploadResult
        viewModelScope.launch {
            viewModelScope.launch {
                try {
                    for (file in selectedImageFile) {
                        val requestFile = file.asRequestBody("image/jpeg".toMediaType())
                        val filePart =
                            MultipartBody.Part.createFormData("file", file.name, requestFile)
                        val description =
                            RequestBody.create("text/plain".toMediaType(), "Image Description")

                        val response = fileUploadApi.uploadFile(token, filePart, description)
                        if (response.isSuccessful) {
                            val fileUploadResponse = response.body()
                            if (fileUploadResponse != null) {
                                if (fileUploadResponse.isSuccess) {
                                    // File upload succeeded
                                    _uploadResult.value =
                                        ResultStatus.Success("File uploaded successfully")
                                } else {
                                    // File upload failed, check server's error message
                                    val errorMsg = fileUploadResponse.returnMsg ?: "Unknown error"
                                    _uploadResult.value =
                                        ResultStatus.Failure("File upload failed: $errorMsg")
                                }
                            } else {
                                // Response body is empty or null
                                _uploadResult.value =
                                    ResultStatus.Failure("File upload failed: Empty response body")
                            }
                        } else {
                            // Request to the server failed
                            _uploadResult.value = ResultStatus.Failure("File upload request failed")
                        }
                    }
                } catch (e: Exception) {
                    // Exception occurred during file upload
                    _uploadResult.value = ResultStatus.Failure("File upload failed: ${e.message}")
                }
            }

        }
    }

    fun String.toMediaTypeOrNull(): MediaType? {
        return try {
            this.toMediaType()
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    // 取消上传
    fun cancelUpload() {
        // 取消当前的上传操作
        // 更新 _uploadResult 为取消状态
    }

    // 清除状态
    fun clearState() {
        _selectedImageFile.value = null
        _uploadProgress.value = 0.0f
        _uploadResult.value = ResultStatus.None
    }
}

sealed class ResultStatus {
    object None : ResultStatus()
    object InProgress : ResultStatus()
    data class Success(val message: String) : ResultStatus()
    data class Failure(val error: String) : ResultStatus()
}


