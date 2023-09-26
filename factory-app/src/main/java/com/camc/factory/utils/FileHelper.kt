package com.camc.factory.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import java.io.File

object FileHelper {
    fun getOrCreateFolder(folderName: String): File? {
        val folder = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
            folderName
        )
        if (!folder.exists()) {
            val created = folder.mkdirs()
            if (!created) {
                return null
            }
        }
        return folder
    }


    fun getImageCount(folder: File, categoryName: String): Int {
        val imageList = folder.listFiles { file ->
            file.name.startsWith(categoryName)
        }
        return imageList?.size ?: 0
    }

    fun createImageFile(folder: File, categoryName: String,contentResolver: ContentResolver): Pair<Uri?, File?> {
        val imageCount = getImageCount(folder, categoryName)
        val fileName = "${categoryName}${imageCount}.jpg"
        val file = File(folder, fileName)

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.DATA, file.absolutePath)
        }
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        return Pair(uri, file)
    }

    fun createSDCardFile(context: Context, categoryName: String): Pair<Uri?, File?> {
        val folder = getOrCreateFolder("Record")
        if (folder != null) {
            val (uri, file) = createImageFile(folder, categoryName, context.contentResolver)
            if (uri == null) {
                showToast(context,"无法创建文件夹")
            } else {
                // 其他处理逻辑...
            }
            return Pair(uri, file)
        } else {
            showToast(context,"无法创建文件夹")
            return Pair(null, null)
        }
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    fun listFilesByCategory(folder: File, categoryName: String): List<File> {
        return folder.listFiles { file ->
            file.name.startsWith(categoryName)
        }?.toList() ?: emptyList()
    }
}
