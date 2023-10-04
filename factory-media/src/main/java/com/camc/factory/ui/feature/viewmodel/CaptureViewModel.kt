package com.camc.factory.ui.feature.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import androidx.camera.core.ImageCapture
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.camc.factory.data.model.entity.MediaRecord
import com.camc.factory.data.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
@HiltViewModel
class CaptureViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : ViewModel() {
    // 保存照片到指定路径，并将路径保存到数据库
    suspend fun savePhoto(photo: Bitmap, categoryId: String) {
        val fileName = generateFileName(categoryId)
        val filePath = saveBitmapToFile(photo, fileName)

        // 将文件路径保存到数据库，这里假设你使用了Room数据库
        val categoryName = categoryId // 获取分类名称，你需要根据实际情况实现该方法
        val mediaRecord = MediaRecord(
            name = categoryName,
            fileName = fileName,
            createTime = System.currentTimeMillis(),
            recorder = fileName
        )
        recordRepository.insertMediaRecord(mediaRecord)
    }
    val _photoCaptureSuccess = MutableLiveData<Boolean>()
    val photoCaptureSuccess: LiveData<Boolean> = _photoCaptureSuccess
    // 调用 capturePhoto 方法，并在回调函数中更新 photoCaptureSuccess 状态

    fun capturePhoto(
        context: Context,
        imageCapture: ImageCapture,
        flashMode: Int,
        categoryId: String,
        param: (Boolean) -> Unit
    ) {
        // 调用 capturePhoto 方法，并在回调函数中更新 photoCaptureSuccess 状态
        capturePhoto(context, imageCapture, flashMode, categoryId) { success ->
            _photoCaptureSuccess.value = success
        }
    }



    // 生成文件名，包括CategoryId和序号
    private suspend fun generateFileName(categoryName: String): String {
        val sequenceNumber = // 获取序号，可以根据已有的照片数量来计算
            return "$categoryName${getSequenceNumber(categoryName)}.jpg"
    }

    // 将Bitmap保存到文件
    private fun saveBitmapToFile(bitmap: Bitmap, fileName: String): String {
        val file = File(Environment.getExternalStorageDirectory(), "media/$fileName")

        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        return file.absolutePath
    }

    // 获取序号，根据已有的照片数量来计算
    // 获取序号，根据已有的照片数量来计算
    private suspend fun getSequenceNumber(name: String): Int {
        val mediaRecordsFlow = recordRepository.getMediaRecordByName(name)
        val mediaRecordsList = mediaRecordsFlow.firstOrNull()
        val photoCount = mediaRecordsList?.size ?: 0
        return photoCount + 1
    }
}