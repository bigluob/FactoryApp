package com.camc.factory.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class Constant {
    private val secretKey = "YourSecretKey" // 替换为你自己的密钥

    private fun encrypt(value: String): String {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        val secretKeySpec = SecretKeySpec(secretKey.toByteArray(), "AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        val encryptedValue = cipher.doFinal(value.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedValue)
    }

    private fun decrypt(encryptedValue: String): String {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        val secretKeySpec = SecretKeySpec(secretKey.toByteArray(), "AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
        val decryptedValue = cipher.doFinal(Base64.getDecoder().decode(encryptedValue))
        return String(decryptedValue)
    }

    private var API_BASE_URL: String by mutableStateOf("http://82.157.66.177:8888/api/Login/Login")
    var server: String
        get() = API_BASE_URL
        set(value) {
            API_BASE_URL = value
        }

    private var _token: String by mutableStateOf("")
    var token: String
        get() = _token
        private set(value) {
            _token = value
        }

    var encryptedServer: String
        get() = encrypt(server)
        set(value) {
            server = decrypt(value)
        }

    var encryptedToken: String
        get() = encrypt(token)
        set(value) {
            token = decrypt(value)
        }

    companion object {
        val API_BASE_URL="http://82.157.66.177:8888/api/Login/"
        const val api = "/api/Login/"
    }
}