package com.fabianofranca.randomuser.resources

import io.modelcontextprotocol.kotlin.sdk.ReadResourceRequest
import io.modelcontextprotocol.kotlin.sdk.TextResourceContents
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ModelsResourceTest {

    @Test
    fun `test resource properties`() {
        // Given
        val resource = ModelsResource()

        // Then
        assertEquals("file:///models.md", resource.uri)
        assertEquals("Random User MCP API Models", resource.name)
        assertTrue(resource.description.contains("Random User MCP API"))
        assertEquals("text/markdown", resource.mimeType)
    }

    @Test
    fun `test handler returns correct content`() = runBlocking {
        // Given
        val resource = ModelsResource()
        val request = ReadResourceRequest("file:///models.md")

        // When
        val result = resource.handler(request)

        // Then
        assertEquals(1, result.contents.size)
        val content = result.contents.first() as TextResourceContents
        assertTrue(content.text.contains("Random User MCP API Models"))
        assertTrue(content.text.contains("Model Hierarchy"))
        assertTrue(content.text.contains("Model Definitions"))
        assertEquals("file:///models.md", content.uri)
        assertEquals("text/markdown", content.mimeType)
    }
}
