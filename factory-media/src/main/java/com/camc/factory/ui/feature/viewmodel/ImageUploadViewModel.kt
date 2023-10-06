package com.camc.factory.ui.feature.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ImageUploadViewModel @Inject constructor(
    //private val apiService: ApiService // 注入 ApiService

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
        // 执行图像上传逻辑，包括网络请求、进度更新等
        // 更新上传进度和上传结果
        _uploadProgress.value = 0.0f // 设置初始进度
        _uploadResult.value = ResultStatus.InProgress // 设置上传中状态

        // 执行上传操作，可能需要使用协程来处理
        // 在上传过程中，更新 _uploadProgress
        // 在上传完成或失败时，更新 _uploadResult
        viewModelScope.launch {
            try {
                // 上传文件，将 Token 作为请求头传递
                for (file in selectedImageFile) {
                    val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    val body = MultipartBody.Part.createFormData("formFile", file.name, requestFile)
                  /* val response = apiService.uploadFile("Bearer $token", body)
                    if (response.isSuccessful) {
                        // 文件上传成功
                        _uploadProgress.value = 1.0f // 设置上传进度为100%
                        _uploadResult.value = ResultStatus.Success("文件上传成功")
                    } else {
                        // 文件上传失败
                        _uploadResult.value = ResultStatus.Failure("文件上传失败")
                    }*/
                }
            } catch (e: Exception) {
                // 发生异常，文件上传失败
                _uploadResult.value = ResultStatus.Failure("文件上传失败")
            }

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


