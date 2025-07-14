package com.fabianofranca.randomuser

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class RandomUserClientTest {

    private val mockEngine = MockEngine { request ->
        // Return a mock response with empty data
        val responseJson = """
            {
                "results": [],
                "info": {
                    "seed": "test",
                    "results": 0,
                    "page": 1,
                    "version": "1.4"
                }
            }
        """.trimIndent()

        respond(
            content = responseJson,
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }

    private fun createClientWithMockEngine(): RandomUserClient {
        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        return RandomUserClient(httpClient)
    }

    @Test
    fun `test getUsers with default parameters`() = runBlocking {
        // Create client with mock engine
        val client = createClientWithMockEngine()

        // Call the method with default parameters
        client.getUsers()

        // Verify the request
        val request = mockEngine.requestHistory.last()

        // Check complete URL with a single assertion
        assertEquals("https://randomuser.me/api/?results=1&page=1&nat=br", request.url.toString())
    }

    @Test
    fun `test getUsers with custom parameters`() = runBlocking {
        // Create client with mock engine
        val client = createClientWithMockEngine()

        // Custom parameter values
        val results = 5
        val page = 2
        val nationality = "us"

        // Call the method with custom parameters
        client.getUsers(results = results, page = page, nationality = nationality)

        // Verify the request
        val request = mockEngine.requestHistory.last()

        // Check complete URL with a single assertion
        assertEquals("https://randomuser.me/api/?results=5&page=2&nat=us", request.url.toString())
    }
}
