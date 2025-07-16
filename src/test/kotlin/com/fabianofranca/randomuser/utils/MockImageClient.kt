package com.fabianofranca.randomuser.utils

import com.fabianofranca.randomuser.ImageClient

class MockImageClient(private val throwException: Boolean = false) : ImageClient {
    var receivedUrl: String? = null

    override suspend fun getImage(url: String): ByteArray {
        receivedUrl = url
        if (throwException) {
            throw Exception("Test exception")
        }
        return ByteArray(1)
    }
}
