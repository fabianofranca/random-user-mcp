package com.fabianofranca.randomuser

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class RandomUserClient {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun getUsers(
        results: Int = 1,
        page: Int = 1,
        nationality: String = "BR"
    ): RandomUserResponse {
        return client.get("https://randomuser.me/api/") {
            url {
                parameters.append("results", results.toString())
                parameters.append("page", page.toString())
                parameters.append("nat", nationality)
            }
        }.body()
    }
}