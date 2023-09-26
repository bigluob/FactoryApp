package com.camc.factory.utils

import java.math.BigInteger
import java.security.MessageDigest
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object EncryptionUtils {
    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES/ECB/PKCS5Padding"
    private const val CHARSET = "UTF-8"

    fun encrypt(data: String, key: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, generateKey(key))
        val encryptedBytes = cipher.doFinal(data.toByteArray(charset(CHARSET)))
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    fun decrypt(data: String, key: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, generateKey(key))
        val decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(data))
        return String(decryptedBytes, charset(CHARSET))
    }

    private fun generateKey(key: String): SecretKeySpec {
        val keyBytes = ByteArray(16)
        val keyData = key.toByteArray(charset(CHARSET))
        System.arraycopy(keyData, 0, keyBytes, 0, Math.min(keyData.size, keyBytes.size))
        return SecretKeySpec(keyBytes, ALGORITHM)
    }
    fun encryptMD5(data: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(data.toByteArray())
        val bigInt = BigInteger(1, digest)
        var md5Hash = bigInt.toString(16)
        while (md5Hash.length < 32) {
            md5Hash = "0$md5Hash"
        }
        return md5Hash
    }
}