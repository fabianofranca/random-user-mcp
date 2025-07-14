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
        results: Int = DEFAULT_RESULTS,
        page: Int = DEFAULT_PAGE,
        nationality: String = DEFAULT_NATIONALITY
    ): RandomUserResponse {
        return client.get("https://randomuser.me/api/") {
            url {
                parameters.append(PARAM_RESULTS, results.toString())
                parameters.append(PARAM_PAGE, page.toString())
                parameters.append(PARAM_NATIONALITY, nationality)
            }
        }.body()
    }

    companion object {
        // API parameter names
        const val PARAM_RESULTS = "results"
        const val PARAM_PAGE = "page"
        const val PARAM_NATIONALITY = "nat"

        // Default values
        const val DEFAULT_RESULTS = 1
        const val DEFAULT_PAGE = 1
        const val DEFAULT_NATIONALITY = "BR"
    }
}
