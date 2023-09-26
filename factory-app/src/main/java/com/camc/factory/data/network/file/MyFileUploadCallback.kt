package com.camc.factory.data.network.file

class MyFileUploadCallback : FileUploadCallback {
    override fun onFileUploadSuccess() {
        // 处理文件上传成功的逻辑
        println("文件上传成功")
    }

    override fun onFileUploadError(message: String) {
        // 处理文件上传错误的逻辑
        println("文件上传错误: $message")
    }
}