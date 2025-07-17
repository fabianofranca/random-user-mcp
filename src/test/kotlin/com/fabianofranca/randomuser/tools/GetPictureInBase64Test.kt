package com.fabianofranca.randomuser.tools

import com.fabianofranca.randomuser.models.GetPictureInBase64Args
import com.fabianofranca.randomuser.utils.GetPictureInBase64ToolTestUtils
import com.fabianofranca.randomuser.utils.MockImageClient
import io.modelcontextprotocol.kotlin.sdk.TextContent
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetPictureInBase64Test {

    @Test
    fun `test tool definition`() {
        // Given: An GetPictureInBase64 instance
        val tool = GetPictureInBase64().tool()

        // When: We get the tool definition
        // (This is implicit as we already have the tool definition from the Given step)

        // Then: The tool properties should match the expected values
        assertEquals("get_picture_in_base64", tool.name)
        assertTrue(tool.description!!.isNotEmpty())
        assertTrue(tool.inputSchema.properties.containsKey(GetPictureInBase64Args::url.name))
        assertTrue(tool.inputSchema.required?.contains(GetPictureInBase64Args::url.name) ?: false)
    }

    @Test
    fun `test handler with valid RandomUser URL`() = runBlocking {
        // Given: An GetPictureInBase64 with a mock client and a request with a valid RandomUser image URL
        val mockClient = MockImageClient()
        val tool = GetPictureInBase64(mockClient)
        val imageUrl = "https://randomuser.me/api/portraits/men/1.jpg"
        val request = GetPictureInBase64ToolTestUtils.createRequest(imageUrl)

        // When: We call the handler
        val result = tool.handler(request)

        // Then: The result should contain the Base64 encoded image
        assertEquals(imageUrl, mockClient.receivedUrl)
        val content = result.content.first() as TextContent
        val decoded = Base64.getDecoder().decode(content.text)
        assertTrue(decoded.isNotEmpty())
    }

    @Test
    fun `test handler with invalid URL format`() = runBlocking {
        // Given: An GetPictureInBase64 and a request with an invalid URL format
        val mockClient = MockImageClient()
        val tool = GetPictureInBase64(mockClient)
        val request = GetPictureInBase64ToolTestUtils.createRequest("invalid-url")

        // When: We call the handler
        val result = tool.handler(request)

        // Then: The result should contain an error message about invalid URL format
        val content = result.content.first() as TextContent
        assertEquals("Invalid image URL format", content.text)
    }

    @Test
    fun `test handler with non-RandomUser URL`() = runBlocking {
        // Given: An GetPictureInBase64 and a request with a valid URL but not from RandomUser
        val mockClient = MockImageClient()
        val tool = GetPictureInBase64(mockClient)
        val imageUrl = "https://example.com/image.jpg"
        val request = GetPictureInBase64ToolTestUtils.createRequest(imageUrl)

        // When: We call the handler
        val result = tool.handler(request)

        // Then: The result should contain an error message about not being a valid RandomUser URL
        val content = result.content.first() as TextContent
        assertEquals("Not a valid RandomUser image URL", content.text)
    }

    @Test
    fun `test handler with exception during image fetching`() = runBlocking {
        // Given: An GetPictureInBase64 with a mock client that throws an exception and a request with a valid RandomUser URL
        val mockClient = MockImageClient(throwException = true)
        val tool = GetPictureInBase64(mockClient)
        val imageUrl = "https://randomuser.me/api/portraits/women/1.jpg"
        val request = GetPictureInBase64ToolTestUtils.createRequest(imageUrl)

        // When: We call the handler
        val result = tool.handler(request)

        // Then: The result should contain an error message about fetching or encoding
        val content = result.content.first() as TextContent
        assertTrue(content.text!!.contains("Error fetching or encoding image"))
    }

    @Test
    fun `test handler with different valid RandomUser URL formats`() = runBlocking {
        // Test different valid RandomUser URL formats
        val validUrls = listOf(
            "https://randomuser.me/api/portraits/men/1.jpg",
            "https://randomuser.me/api/portraits/women/2.jpg",
            "https://randomuser.me/api/portraits/med/men/3.jpg",
            "https://randomuser.me/api/portraits/med/women/4.jpg",
            "https://randomuser.me/api/portraits/thumb/men/5.jpg",
            "https://randomuser.me/api/portraits/thumb/women/6.jpg"
        )

        for (url in validUrls) {
            // Given: An GetPictureInBase64 with a mock client and a request with a valid RandomUser image URL
            val mockClient = MockImageClient()
            val tool = GetPictureInBase64(mockClient)
            val request = GetPictureInBase64ToolTestUtils.createRequest(url)

            // When: We call the handler
            val result = tool.handler(request)

            // Then: The result should contain the Base64 encoded image
            assertEquals(url, mockClient.receivedUrl)
            val content = result.content.first() as TextContent
            val decoded = Base64.getDecoder().decode(content.text)
            assertTrue(decoded.isNotEmpty(), "URL $url should be valid")
        }
    }
}
