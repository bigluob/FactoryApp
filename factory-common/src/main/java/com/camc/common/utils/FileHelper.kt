package com.camc.common.utils

import android.content.Context
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FileHelper(private val context: Context) {

    private val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
    val storageDir by lazy {
        val dir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
            "FactMediaRecord"
        )
        if (!dir.exists() && !dir.mkdirs()) {
            // 处理目录创建失败的情况
            throw IOException("无法创建目录")
        }
        dir
    }


    init {
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }
    }

    // 创建子文件夹
    fun createSubFolder(categoryName: String): File {
        val subFolder = File(storageDir, categoryName)
        if (!subFolder.exists()) {
            subFolder.mkdirs()
        }
        return subFolder
    }

    // 读取文件列表
    fun getMediaFiles(categoryName: String): List<File> {
        val subFolder = createSubFolder(categoryName)
        return subFolder.listFiles()?.toList() ?: emptyList()
    }

    // 创建照片文件
    @Throws(IOException::class)
    fun createImageFile(categoryName: String): File {
        // 这里可以使用 fileHelper 来获取 categoryName 等信息
        val subFolder = createSubFolder(categoryName)
        val imageFileName = "IMG_${categoryName}_${dateFormat.format(Date())}".replace(":", "_")
        val imageFile = File.createTempFile(imageFileName, ".jpg", subFolder)
        return imageFile
    }

    // 获取拍照意图，通过 FileProvider 获取 URI
    fun getCaptureImageIntent(imageFile: File): Pair<File, String> {
        val authority = "${context.packageName}.fileprovider"
        val uri = FileProvider.getUriForFile(context, authority, imageFile)
        return Pair(imageFile, uri.toString())
    }
}
