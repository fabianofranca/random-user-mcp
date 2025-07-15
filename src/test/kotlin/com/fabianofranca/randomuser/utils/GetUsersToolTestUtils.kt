package com.fabianofranca.randomuser.utils

import com.fabianofranca.randomuser.models.GetUsersArgs
import com.fabianofranca.randomuser.models.RandomUserResponse
import com.fabianofranca.randomuser.tools.GetUsersTool
import io.modelcontextprotocol.kotlin.sdk.CallToolRequest
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.TextContent
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.assertEquals

/**
 * Utility class containing helper methods for testing GetUsersTool
 */
object GetUsersToolTestUtils {

    /**
     * Creates a GetUsersTool with a mock client for testing
     */
    fun createToolWithMockClient(
        mockResponse: RandomUserResponse? = null,
        throwException: Boolean = false
    ): Pair<GetUsersTool, MockRandomUserClient> {
        val mockClient = MockRandomUserClient(mockResponse, throwException)
        val tool = GetUsersTool(mockClient)
        return Pair(tool, mockClient)
    }

    /**
     * Creates a CallToolRequest with the specified parameters
     */
    fun createRequest(
        results: Int? = null,
        page: Int? = null,
        nationality: String? = null,
        version: String? = null
    ): CallToolRequest {
        val arguments = mutableMapOf<String, JsonPrimitive>()

        results?.let { arguments[GetUsersArgs.PARAM_RESULTS] = JsonPrimitive(it) }
        page?.let { arguments[GetUsersArgs.PARAM_PAGE] = JsonPrimitive(it) }
        nationality?.let { arguments[GetUsersArgs.PARAM_NATIONALITY] = JsonPrimitive(it) }
        version?.let { arguments[GetUsersArgs.PARAM_VERSION] = JsonPrimitive(it) }

        return CallToolRequest(
            name = "get_users",
            arguments = JsonObject(arguments)
        )
    }

    /**
     * Verifies that the mock client was called with the expected parameters
     */
    fun verifyParameters(
        mockClient: MockRandomUserClient,
        expectedResults: Int,
        expectedPage: Int,
        expectedNationality: String,
        expectedVersion: String = GetUsersArgs.DEFAULT_VERSION
    ) {
        assertEquals(expectedResults, mockClient.capturedResults)
        assertEquals(expectedPage, mockClient.capturedPage)
        assertEquals(expectedNationality, mockClient.capturedNationality)
        assertEquals(expectedVersion, mockClient.capturedVersion)
    }

    /**
     * Verifies that the JSON response matches the expected response
     */
    fun verifyJsonResponse(
        result: CallToolResult,
        expectedResponse: RandomUserResponse
    ) {
        val content = result.content.first() as TextContent
        val responseJson = content.text

        val expectedJson = Json.encodeToString(RandomUserResponse.serializer(), expectedResponse)

        assertEquals(expectedJson, responseJson)
    }
}
