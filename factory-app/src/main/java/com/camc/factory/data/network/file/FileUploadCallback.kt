package com.camc.factory.data.network.file

interface FileUploadCallback {
    fun onFileUploadSuccess()
    fun onFileUploadError(message: String)
}