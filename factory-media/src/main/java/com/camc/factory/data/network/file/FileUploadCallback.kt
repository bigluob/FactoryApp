package com.camc.factory.data.network.file

interface FileUploadCallback {
    abstract val progressState: Int

    fun onFileUploadSuccess()
    fun onFileUploadError(message: String)
}