package com.fabianofranca.randomuser

import com.fabianofranca.randomuser.models.*
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RandomUserClientImplTest {

    private val json = Json { ignoreUnknownKeys = true }

    private fun createMockEngine(
        responseContent: String,
        status: HttpStatusCode = HttpStatusCode.OK
    ): MockEngine {
        return MockEngine { request ->
            respond(
                content = responseContent,
                status = status,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
    }

    private fun createClientWithMockEngine(mockEngine: MockEngine): RandomUserClientImpl {
        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        return RandomUserClientImpl(httpClient)
    }

    @Test
    fun `test successful response handling`() = runBlocking {
        // Given: A mock response with user data
        val expectedResponse = RandomUserResponse(
            results = listOf(
                UserResult(
                    gender = "male",
                    name = UserName(title = "Mr", first = "John", last = "Doe"),
                    location = UserLocation(
                        street = UserStreet(number = 123, name = "Main St"),
                        city = "New York",
                        state = "NY",
                        country = "USA",
                        postcode = "10001",
                        coordinates = UserCoordinates(latitude = "40.7128", longitude = "-74.0060"),
                        timezone = UserTimezone(offset = "-5:00", description = "Eastern Time")
                    ),
                    email = "john.doe@example.com",
                    login = UserLogin(
                        uuid = "12345",
                        username = "johndoe",
                        password = "password",
                        salt = "salt",
                        md5 = "md5",
                        sha1 = "sha1",
                        sha256 = "sha256"
                    ),
                    dob = UserDob(date = "1990-01-01", age = 33),
                    registered = UserRegistered(date = "2010-01-01", age = 13),
                    phone = "123-456-7890",
                    cell = "098-765-4321",
                    id = UserId(name = "SSN", value = "123-45-6789"),
                    picture = UserPicture(
                        large = "https://example.com/large.jpg",
                        medium = "https://example.com/medium.jpg",
                        thumbnail = "https://example.com/thumbnail.jpg"
                    ),
                    nat = "US"
                )
            ),
            info = Info(
                seed = "test-seed",
                results = 1,
                page = 1,
                version = "1.4"
            )
        )
        val responseContent = json.encodeToString(expectedResponse)
        val mockEngine = createMockEngine(responseContent)
        val client = createClientWithMockEngine(mockEngine)

        // When: We call getUsers
        val response = client.getUsers()

        // Then: The response should be correctly parsed
        assertEquals(expectedResponse, response)
        assertEquals(1, response.results.size)
        assertEquals("John", response.results.first().name.first)
        assertEquals("Doe", response.results.first().name.last)
    }

    @Test
    fun `test error response handling`() {
        // Given: A mock engine that returns an error response
        val errorResponse = """{"error": "Invalid request"}"""
        val mockEngine = createMockEngine(errorResponse, HttpStatusCode.BadRequest)
        val client = createClientWithMockEngine(mockEngine)

        // When/Then: Calling getUsers should throw an exception
        runBlocking {
            assertFailsWith<Exception> {
                client.getUsers()
            }
        }
    }

    @Test
    fun `test network error handling`() {
        // Given: A mock engine that throws an exception
        val mockEngine = MockEngine {
            throw Exception("Network error")
        }
        val client = createClientWithMockEngine(mockEngine)

        // When/Then: Calling getUsers should throw an exception
        runBlocking {
            assertFailsWith<Exception> {
                client.getUsers()
            }
        }
    }

    @Test
    fun `test custom parameters are passed correctly`() = runBlocking {
        // Given: A mock engine that captures the request URL
        val mockEngine = MockEngine { request ->
            // Return a simple valid response
            val responseContent = """
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
                content = responseContent,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val client = createClientWithMockEngine(mockEngine)
        val args = GetUsersArgs(results = 10, page = 2, nationality = "ca", version = "1.3")

        // When: We call getUsers with custom parameters
        client.getUsers(args)

        // Then: The request URL should contain the custom parameters
        val request = mockEngine.requestHistory.last()
        assertEquals("https://randomuser.me/api/1.3/?results=10&page=2&nat=ca", request.url.toString())
    }
}
