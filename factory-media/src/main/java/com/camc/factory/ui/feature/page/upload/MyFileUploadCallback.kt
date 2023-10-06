package com.camc.factory.ui.feature.page.upload

class MyFileUploadCallback(override val progressState: Int) : FileUploadCallback {
    override fun onFileUploadSuccess() {
        // 处理文件上传成功的逻辑
        println("文件上传成功")
    }

    override fun onFileUploadError(message: String) {
        // 处理文件上传错误的逻辑
        println("文件上传错误: $message")
    }
}