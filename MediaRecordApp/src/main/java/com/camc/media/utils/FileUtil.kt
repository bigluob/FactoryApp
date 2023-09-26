package com.camc.media.utils

import java.io.File

class FileUtil {
    fun createFolder(folderPath: String): Boolean {
        val folder = File(folderPath)
        return if (!folder.exists()) {
            folder.mkdirs()
        } else {
            false
        }
    }

    fun getFileList(directoryPath: String): List<File> {
        val directory = File(directoryPath)
        return if (directory.exists() && directory.isDirectory) {
            directory.listFiles()?.toList() ?: emptyList()
        } else {
            emptyList()
        }
    }

    fun readFile(filePath: String): String? {
        val file = File(filePath)
        return if (file.exists() && file.isFile) {
            file.readText()
        } else {
            null
        }
    }

    fun writeFile(filePath: String, content: String): Boolean {
        val file = File(filePath)
        return try {
            file.writeText(content)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}