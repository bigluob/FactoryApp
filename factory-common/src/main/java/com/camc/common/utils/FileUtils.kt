package com.camc.common.utils

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object FileUtils {
    fun saveFile(file: File, data: ByteArray) {
        FileOutputStream(file).use { outputStream ->
            outputStream.write(data)
        }
    }

    fun readFile(file: File): ByteArray {
        return FileInputStream(file).use { inputStream ->
            inputStream.readBytes()
        }
    }

    fun deleteFile(file: File) {
        file.delete()
    }
}