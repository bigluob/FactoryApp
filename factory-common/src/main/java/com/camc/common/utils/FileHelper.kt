package com.camc.common.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.File

class FileHelper(private val context: Context) {

    private var folder: File? = null

    fun getOrCreateFolder(context: Context, categoryName: String): String? {
        val mediaRecordDir = File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM), "MediaRecord")
        val categoryDir = File(mediaRecordDir, categoryName)

        if (!categoryDir.exists()) {
            val created = categoryDir.mkdirs()
            if (!created) {
                return null
            }
        }
        folder = categoryDir // 将创建的文件夹赋值给全局变量
        return categoryDir.absolutePath
    }
    // 在 FileHelper 类中修改 listFilesByCategory 方法
    suspend fun listFilesByCategory(context: Context, categoryName: String): Pair<List<File>, Int> {
        val folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val files = folder.listFiles { dir, name ->
            name.startsWith("$categoryName(") && name.endsWith(").jpg")
        }
        return if (folder.exists() && folder.isDirectory) {
            val files = folder.listFiles()?.filter { file ->
                file.name.startsWith(categoryName)
            } ?: emptyList()
            Pair(files, files.size)
        } else {
            Pair(emptyList(), 0)
        }
    }


    fun getImageCount(categoryName: String): Int {
        val imageList = folder?.listFiles { file ->
            file.name.startsWith(categoryName)
        }
        return imageList?.size ?: 0
    }

    fun createImageFile(categoryName: String, contentResolver: ContentResolver): Uri? {
        val imageCount = getImageCount(categoryName)
        val fileName = "${categoryName}${imageCount}.jpg"
        val file = File(folder, fileName)

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.DATA, file.absolutePath)
        }
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        return uri
    }
}