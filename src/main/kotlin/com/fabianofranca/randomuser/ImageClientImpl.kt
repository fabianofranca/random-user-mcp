package com.fabianofranca.randomuser

import com.fabianofranca.com.fabianofranca.randomuser.DI
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class ImageClientImpl(private val client: HttpClient = DI.httpClient) : ImageClient {
    override suspend fun getImage(url: String): ByteArray {
        val response: HttpResponse = client.get(url)
        val imageBytes = response.readRawBytes()
        return imageBytes
    }
}
