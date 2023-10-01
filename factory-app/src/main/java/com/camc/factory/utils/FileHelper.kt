package com.camc.factory.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File

object FileHelper {

    private var folder: File? = null

    /**
     * 获取或创建指定名称的文件夹
     * @param context 上下文对象
     * @param dicName 文件夹名称
     * @return 文件夹路径，如果创建失败则返回 null
     */
    fun getOrCreateFolder(context: Context, dicName: String): File? {
        val mediaRecordDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "MediaRecord")
        val categoryDir = File(mediaRecordDir, dicName)
        if (!categoryDir.exists()) {
            val created = categoryDir.mkdirs()
            if (!created) {
                return null
            }
        }
        return categoryDir
    }

    /**
     * 获取文件夹下的文件数量
     * @param categoryName 文件分类名称
     * @return 文件数量
     */
    fun getImageCount(categoryName: String): Int {
        val imageList = folder?.listFiles { file ->
            file.name.startsWith(categoryName)
        }
        return imageList?.size ?: 0
    }

    /**
     * 创建图片文件并插入到媒体库
     * @param categoryName 图片分类名称
     * @param contentResolver ContentResolver 对象，用于插入图片到媒体库
     * @return 插入的 Uri，如果创建失败则为 null
     */
    fun createImageFile(categoryName: String, contentResolver: ContentResolver): Uri? {
        val imageCount = getImageCount(categoryName)
        val fileName = "${categoryName}${imageCount}.jpg"
        val file = File(folder, fileName)

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.DATA, file.absolutePath)
        }
        val uri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        return uri
    }


    fun listFilesByCategory(folderPath: String, categoryName: String): List<File> {
        val folder = File(folderPath)
        return if (folder.exists() && folder.isDirectory) {
            folder.listFiles()?.filter { it.name.contains(categoryName) } ?: emptyList()
        } else {
            emptyList()
        }
    }
    fun listFilesByCategory(categoryName: String): List<File> {
        if (folder == null) {
            throw IllegalStateException("Folder is not initialized. Call getOrCreateFolder() first.")
        }
        return folder!!.listFiles { file ->
            file.name.startsWith(categoryName)
        }?.toList() ?: emptyList()
    }

    /*获取系统默认存储路径*/
    private fun getDcimDirectory(context: Context, dicName: String): File {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10及更高版本使用应用程序特定目录
            File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM), dicName)
        } else {
            // Android 9及更低版本使用外部存储目录
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                dicName
            )
        }
    }
}
