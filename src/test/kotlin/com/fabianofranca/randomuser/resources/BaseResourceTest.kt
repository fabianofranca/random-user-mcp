package com.fabianofranca.randomuser.resources

import io.modelcontextprotocol.kotlin.sdk.ReadResourceRequest
import io.modelcontextprotocol.kotlin.sdk.ReadResourceResult
import io.modelcontextprotocol.kotlin.sdk.TextResourceContents
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BaseResourceTest {

    private class TestResource : BaseResource() {
        override val uri: String = "file:///test.txt"
        override val name: String = "Test Resource"
        override val description: String = "A test resource"
        override val mimeType: String = "text/plain"

        var handlerCalled = false

        override suspend fun handler(request: ReadResourceRequest): ReadResourceResult {
            handlerCalled = true
            return ReadResourceResult(
                contents = listOf(
                    TextResourceContents(
                        text = "Test content",
                        uri = request.uri,
                        mimeType = "text/plain"
                    )
                )
            )
        }
    }

    @Test
    fun `test resource properties`() {
        // Given
        val resource = TestResource()

        // Then
        assertEquals("file:///test.txt", resource.uri)
        assertEquals("Test Resource", resource.name)
        assertEquals("A test resource", resource.description)
        assertEquals("text/plain", resource.mimeType)
    }

    @Test
    fun `test handler is called with correct request`() = runBlocking {
        // Given
        val resource = TestResource()
        val request = ReadResourceRequest("file:///test.txt")

        // When
        val result = resource.handler(request)

        // Then
        assertTrue(resource.handlerCalled)
        assertEquals(1, result.contents.size)
        val content = result.contents.first() as TextResourceContents
        assertEquals("Test content", content.text)
        assertEquals("file:///test.txt", content.uri)
        assertEquals("text/plain", content.mimeType)
    }
}
