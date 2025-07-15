package com.fabianofranca.randomuser.tools

import com.fabianofranca.randomuser.models.GetUsersArgs
import com.fabianofranca.randomuser.utils.GetUsersToolTestUtils.createRequest
import com.fabianofranca.randomuser.utils.GetUsersToolTestUtils.createToolWithMockClient
import com.fabianofranca.randomuser.utils.GetUsersToolTestUtils.verifyJsonResponse
import com.fabianofranca.randomuser.utils.GetUsersToolTestUtils.verifyParameters
import io.modelcontextprotocol.kotlin.sdk.TextContent
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetUsersToolTest {
    @Test
    fun `test tool definition`() {
        // Given: A GetUsersTool instance
        val tool = GetUsersTool().tool()

        // When: We get the tool definition
        // (This is implicit as we already have the tool definition from the Given step)

        // Then: The tool properties should match the expected values
        // Verify basic tool properties
        assertEquals("get_users", tool.name)
        assertTrue(tool.description!!.isNotEmpty())

        // Verify input schema properties exist
        val properties = tool.inputSchema.properties
        assertTrue(properties.toString().contains(GetUsersArgs.PARAM_RESULTS))
        assertTrue(properties.toString().contains(GetUsersArgs.PARAM_PAGE))
        assertTrue(properties.toString().contains(GetUsersArgs.PARAM_NATIONALITY))
        assertTrue(properties.toString().contains(GetUsersArgs.PARAM_VERSION))
        assertTrue(properties.toString().contains(GetUsersArgs.PARAM_SEED))
        assertTrue(properties.toString().contains(GetUsersArgs.PARAM_GENDER))
        assertTrue(properties.toString().contains(GetUsersArgs.PARAM_PASSWORD))
        assertTrue(properties.toString().contains(GetUsersArgs.PARAM_INCLUDE))
        assertTrue(properties.toString().contains(GetUsersArgs.PARAM_EXCLUDE))

        // Verify that the properties contain the expected types and default values
        val propertiesString = properties.toString()
        assertTrue(propertiesString.contains("\"type\":\"number\""))
        assertTrue(propertiesString.contains("\"default\":${GetUsersArgs.DEFAULT_RESULTS}"))
        assertTrue(propertiesString.contains("\"default\":${GetUsersArgs.DEFAULT_PAGE}"))
        assertTrue(propertiesString.contains("\"default\":\"${GetUsersArgs.DEFAULT_NATIONALITY}\""))
        assertTrue(propertiesString.contains("\"default\":\"${GetUsersArgs.DEFAULT_VERSION}\""))
    }

    @Test
    fun `test handler with default parameters`() = runBlocking {
        // Given: A GetUsersTool with a mock client and a request with default parameters
        val (tool, mockClient) = createToolWithMockClient()
        val request = createRequest()

        // When: We call the handler
        val result = tool.handler(request)

        // Then: The client should be called with default parameters and return the expected response
        // Verify client was called with default parameters
        verifyParameters(
            mockClient,
            GetUsersArgs.DEFAULT_RESULTS,
            GetUsersArgs.DEFAULT_PAGE,
            GetUsersArgs.DEFAULT_NATIONALITY
        )

        // Verify JSON response
        verifyJsonResponse(result, mockClient.expectedResponse)
    }

    @Test
    fun `test handler with custom parameters`() = runBlocking {
        // Given: A GetUsersTool with a mock client and a request with custom parameters
        val results = 5
        val page = 2
        val nationality = "br"
        val (tool, mockClient) = createToolWithMockClient()
        val request = createRequest(results, page, nationality)

        // When: We call the handler
        val result = tool.handler(request)

        // Then: The client should be called with custom parameters and return the expected response
        // Verify client was called with custom parameters
        verifyParameters(mockClient, results, page, nationality)

        // Verify JSON response
        verifyJsonResponse(result, mockClient.expectedResponse)
    }

    @Test
    fun `test handler with custom version parameter`() = runBlocking {
        // Given: A GetUsersTool with a mock client and a request with custom version parameter
        val version = "1.3"
        val (tool, mockClient) = createToolWithMockClient()
        val request = createRequest(version = version)

        // When: We call the handler
        val result = tool.handler(request)

        // Then: The client should be called with custom version parameter and return the expected response
        // Verify client was called with custom version parameter
        verifyParameters(
            mockClient,
            GetUsersArgs.DEFAULT_RESULTS,
            GetUsersArgs.DEFAULT_PAGE,
            GetUsersArgs.DEFAULT_NATIONALITY,
            version
        )

        // Verify JSON response
        verifyJsonResponse(result, mockClient.expectedResponse)
    }

    @Test
    fun `test handler with error`() = runBlocking {
        // Given: A GetUsersTool with a mock client that throws an exception and a request
        val (tool, _) = createToolWithMockClient(throwException = true)
        val request = createRequest()

        // When: We call the handler
        val result = tool.handler(request)

        // Then: The result should contain an error message
        val content = result.content.first() as TextContent
        val errorMessage = content.text ?: ""
        assertContains(errorMessage, "Error getting users: Test exception")
    }

    @Test
    fun `test handler with seed parameter`() = runBlocking {
        // Given: A GetUsersTool with a mock client and a request with a seed parameter
        val seed = "abc123"
        val (tool, mockClient) = createToolWithMockClient()
        val request = createRequest(seed = seed)

        // When: We call the handler
        val result = tool.handler(request)

        // Then: The client should be called with the seed parameter and return the expected response
        // Verify client was called with the seed parameter
        verifyParameters(
            mockClient,
            GetUsersArgs.DEFAULT_RESULTS,
            GetUsersArgs.DEFAULT_PAGE,
            GetUsersArgs.DEFAULT_NATIONALITY,
            GetUsersArgs.DEFAULT_VERSION,
            seed
        )

        // Verify JSON response
        verifyJsonResponse(result, mockClient.expectedResponse)
    }

    @Test
    fun `test handler with gender parameter`() = runBlocking {
        // Given: A GetUsersTool with a mock client and a request with a gender parameter
        val gender = "female"
        val (tool, mockClient) = createToolWithMockClient()
        val request = createRequest(gender = gender)

        // When: We call the handler
        val result = tool.handler(request)

        // Then: The client should be called with the gender parameter and return the expected response
        // Verify client was called with the gender parameter
        verifyParameters(
            mockClient,
            GetUsersArgs.DEFAULT_RESULTS,
            GetUsersArgs.DEFAULT_PAGE,
            GetUsersArgs.DEFAULT_NATIONALITY,
            GetUsersArgs.DEFAULT_VERSION,
            null,
            gender
        )

        // Verify JSON response
        verifyJsonResponse(result, mockClient.expectedResponse)
    }

    @Test
    fun `test handler with password parameter`() = runBlocking {
        // Given: A GetUsersTool with a mock client and a request with a password parameter
        val password = "upper,lower,1-16"
        val (tool, mockClient) = createToolWithMockClient()
        val request = createRequest(password = password)

        // When: We call the handler
        val result = tool.handler(request)

        // Then: The client should be called with the password parameter and return the expected response
        // Verify client was called with the password parameter
        verifyParameters(
            mockClient,
            GetUsersArgs.DEFAULT_RESULTS,
            GetUsersArgs.DEFAULT_PAGE,
            GetUsersArgs.DEFAULT_NATIONALITY,
            GetUsersArgs.DEFAULT_VERSION,
            null,
            null,
            password
        )

        // Verify JSON response
        verifyJsonResponse(result, mockClient.expectedResponse)
    }

    @Test
    fun `test handler with include parameter`() = runBlocking {
        // Given: A GetUsersTool with a mock client and a request with an include parameter
        val include = "gender,name,nat"
        val (tool, mockClient) = createToolWithMockClient()
        val request = createRequest(include = include)

        // When: We call the handler
        val result = tool.handler(request)

        // Then: The client should be called with the include parameter and return the expected response
        // Verify client was called with the include parameter
        verifyParameters(
            mockClient,
            GetUsersArgs.DEFAULT_RESULTS,
            GetUsersArgs.DEFAULT_PAGE,
            GetUsersArgs.DEFAULT_NATIONALITY,
            GetUsersArgs.DEFAULT_VERSION,
            null,
            null,
            null,
            include
        )

        // Verify JSON response
        verifyJsonResponse(result, mockClient.expectedResponse)
    }

    @Test
    fun `test handler with exclude parameter`() = runBlocking {
        // Given: A GetUsersTool with a mock client and a request with an exclude parameter
        val exclude = "login"
        val (tool, mockClient) = createToolWithMockClient()
        val request = createRequest(exclude = exclude)

        // When: We call the handler
        val result = tool.handler(request)

        // Then: The client should be called with the exclude parameter and return the expected response
        // Verify client was called with the exclude parameter
        verifyParameters(
            mockClient,
            GetUsersArgs.DEFAULT_RESULTS,
            GetUsersArgs.DEFAULT_PAGE,
            GetUsersArgs.DEFAULT_NATIONALITY,
            GetUsersArgs.DEFAULT_VERSION,
            null,
            null,
            null,
            null,
            exclude
        )

        // Verify JSON response
        verifyJsonResponse(result, mockClient.expectedResponse)
    }
}
