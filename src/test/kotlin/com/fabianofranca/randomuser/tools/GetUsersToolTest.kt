package com.fabianofranca.randomuser.tools

import com.fabianofranca.randomuser.RandomUserClient
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
        assertTrue(properties.toString().contains(RandomUserClient.PARAM_RESULTS))
        assertTrue(properties.toString().contains(RandomUserClient.PARAM_PAGE))
        assertTrue(properties.toString().contains(RandomUserClient.PARAM_NATIONALITY))

        // Verify that the properties contain the expected types and default values
        val propertiesString = properties.toString()
        assertTrue(propertiesString.contains("\"type\":\"number\""))
        assertTrue(propertiesString.contains("\"default\":${RandomUserClient.DEFAULT_RESULTS}"))
        assertTrue(propertiesString.contains("\"default\":${RandomUserClient.DEFAULT_PAGE}"))
        assertTrue(propertiesString.contains("\"default\":\"${RandomUserClient.DEFAULT_NATIONALITY}\""))
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
            RandomUserClient.DEFAULT_RESULTS,
            RandomUserClient.DEFAULT_PAGE,
            RandomUserClient.DEFAULT_NATIONALITY
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
}
