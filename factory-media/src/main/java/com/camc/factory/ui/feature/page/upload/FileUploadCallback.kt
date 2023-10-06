package com.camc.factory.ui.feature.page.upload

interface FileUploadCallback {
    abstract val progressState: Int

    fun onFileUploadSuccess()
    fun onFileUploadError(message: String)
}