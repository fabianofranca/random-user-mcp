package com.fabianofranca.randomuser

import com.fabianofranca.randomuser.models.GetUsersArgs
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
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
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }

        return RandomUserClientImpl(httpClient)
    }

    @Test
    fun `test getUsers with default parameters`() = runBlocking {
        // Given: A RandomUserClient with a mock engine
        val client = createClientWithMockEngine()

        // When: We call getUsers with default parameters
        client.getUsers()

        // Then: The request should have the expected URL with default parameters
        val request = mockEngine.requestHistory.last()
        assertEquals("https://randomuser.me/api/1.4/?results=1&page=1&nat=us", request.url.toString())
    }

    @Test
    fun `test getUsers with custom parameters`() = runBlocking {
        // Given: A RandomUserClient with a mock engine and custom parameter values
        val client = createClientWithMockEngine()
        val results = 5
        val page = 2
        val nationality = "br"
        val version = "1.2"

        // When: We call getUsers with custom parameters
        client.getUsers(GetUsersArgs(results = results, page = page, nationality = nationality, version = version))

        // Then: The request should have the expected URL with custom parameters
        val request = mockEngine.requestHistory.last()
        assertEquals("https://randomuser.me/api/1.2/?results=5&page=2&nat=br", request.url.toString())
    }
}
