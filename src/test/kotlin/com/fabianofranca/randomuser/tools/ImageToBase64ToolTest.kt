package com.fabianofranca.randomuser.tools

import com.fabianofranca.randomuser.models.ImageToBase64Args
import com.fabianofranca.randomuser.utils.ImageToBase64ToolTestUtils
import com.fabianofranca.randomuser.utils.MockImageClient
import io.modelcontextprotocol.kotlin.sdk.TextContent
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ImageToBase64ToolTest {

    @Test
    fun `test tool definition`() {
        // Given: An ImageToBase64Tool instance
        val tool = ImageToBase64Tool().tool()

        // When: We get the tool definition
        // (This is implicit as we already have the tool definition from the Given step)

        // Then: The tool properties should match the expected values
        assertEquals("image_to_base64", tool.name)
        assertTrue(tool.description!!.isNotEmpty())
        assertTrue(tool.inputSchema.properties.containsKey(ImageToBase64Args::url.name))
        assertTrue(tool.inputSchema.required?.contains(ImageToBase64Args::url.name) ?: false)
    }

    @Test
    fun `test handler`() = runBlocking {
        // Given: An ImageToBase64Tool with a mock client and a request with a valid image URL
        val mockClient = MockImageClient()
        val tool = ImageToBase64Tool(mockClient)
        val imageUrl = "https://via.placeholder.com/1.png"
        val request = ImageToBase64ToolTestUtils.createRequest(imageUrl)

        // When: We call the handler
        val result = tool.handler(request)

        // Then: The result should contain the Base64 encoded image
        assertEquals(imageUrl, mockClient.receivedUrl)
        val content = result.content.first() as TextContent
        val decoded = Base64.getDecoder().decode(content.text)
        assertTrue(decoded.isNotEmpty())
    }

    @Test
    fun `test handler with invalid url`() = runBlocking {
        // Given: An ImageToBase64Tool with a mock client that throws an exception and a request
        val mockClient = MockImageClient(throwException = true)
        val tool = ImageToBase64Tool(mockClient)
        val request = ImageToBase64ToolTestUtils.createRequest("invalid-url")

        // When: We call the handler
        val result = tool.handler(request)

        // Then: The result should contain an error message
        val content = result.content.first() as TextContent
        assertTrue(content.text!!.contains("Error fetching or encoding image"))
    }
}
