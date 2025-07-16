package com.fabianofranca.randomuser

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class ImageClientImpl(private val client: HttpClient = HttpClient(CIO)) : ImageClient {
    override suspend fun getImage(url: String): ByteArray {
        val response: HttpResponse = client.get(url)
        val imageBytes = response.readRawBytes()
        client.close()
        return imageBytes
    }
}
