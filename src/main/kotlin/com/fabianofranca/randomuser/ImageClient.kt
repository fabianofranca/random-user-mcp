package com.fabianofranca.randomuser

interface ImageClient {
    suspend fun getImage(url: String): ByteArray
}
