package com.camc.factory.common

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class Constant {
    companion object {
        // 服务器地址和端口
        const val BASE_URL = "http://82.157.66.177:8888"
        // 登录接口
        const val LOGIN_PATH = "/api/Login/"
        //文件上传接口
        const val UPLOAD_PATH = "/api/Common/SingleFileUpload/"

        // 密钥
        private const val SECRET_KEY = "YourSecretKey"

        // 加密和解密方法
        fun encrypt(value: String): String {
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            val secretKeySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
            val encryptedValue = cipher.doFinal(value.toByteArray())
            return Base64.getEncoder().encodeToString(encryptedValue)
        }

        fun decrypt(encryptedValue: String): String {
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            val secretKeySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
            val decryptedValue = cipher.doFinal(Base64.getDecoder().decode(encryptedValue))
            return String(decryptedValue)
        }
    }
}
