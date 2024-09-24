package com.openmindvalley.android.app.utils

import android.util.Log
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object Util {

    val LIVE_SCORE_IV = "6119443eb39dc954"
    val LIVE_SCORE_SECRET_KEY = "Lk5Uz3slx3BrAghS1=aW5AYgWZRV0tIX"

    fun decryptLiveScoreData(encryptedText: String): String {
        return try {
            val base64DecodedData = String(Base64.decode(encryptedText,0))
            val removedIvFromBase64DecodedData = base64DecodedData.substring(LIVE_SCORE_IV.length)

            val decryptedBytes = decryptAES(removedIvFromBase64DecodedData, LIVE_SCORE_IV, LIVE_SCORE_SECRET_KEY)
            val decodeBytes = Base64.decode(decryptedBytes, 0)

            val output = String(decodeBytes)
            output
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun decryptAES(encryptedText: String, iv: String, secretKey: String): String {

        val ALGORITHM_AES = "AES"
        val TRANSFORMATION_AES_CTR_NO_PADDING = "AES/CTR/NoPadding"

        return try {
            val secretKeySpec = SecretKeySpec(secretKey.toByteArray(), ALGORITHM_AES)
            val ivParameterSpec = IvParameterSpec(iv.toByteArray())

            val cipher = Cipher.getInstance(TRANSFORMATION_AES_CTR_NO_PADDING)
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
            val decryptedByteArray = cipher.doFinal(hexToBytes(encryptedText))

            String(decryptedByteArray, Charsets.UTF_8)
        } catch (e: Exception) {
            ""
        }
    }

    private fun hexToBytes(str: String?): ByteArray? {
        return if (str == null) {
            null
        } else if (str.length < 2) {
            null
        } else {
            val len = str.length / 2
            val buffer = ByteArray(len)
            for (i in 0 until len) {
                buffer[i] = str.substring(i * 2, i * 2 + 2).toInt(16).toByte()
            }
            buffer
        }
    }
}