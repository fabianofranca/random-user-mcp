package com.fabianofranca.randomuser.tools

import com.fabianofranca.randomuser.utils.GetUsersToolTestUtils
import io.modelcontextprotocol.kotlin.sdk.TextContent
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetModelsDocToolTest {

    @Test
    fun `test tool definition`() {
        // Given: A GetModelsDocTool instance
        val tool = GetModelsDocTool().tool()

        // When: We get the tool definition
        // (This is implicit as we already have the tool definition from the Given step)

        // Then: The tool properties should match the expected values
        assertEquals("get_models_doc", tool.name)
        assertTrue(tool.description!!.isNotEmpty())
        assertTrue(tool.inputSchema.properties.isEmpty())
        assertTrue(tool.inputSchema.required?.isEmpty() ?: true)
    }

    @Test
    fun `test handler`() = runBlocking {
        // Given: A GetModelsDocTool and a request
        val tool = GetModelsDocTool()
        val request = GetUsersToolTestUtils.createRequest()

        // When: We call the handler
        val result = tool.handler(request)

        // Then: The result should contain the content of the models.md file
        val content = result.content.first() as TextContent
        val modelsContent = this::class.java.getResource("/models.md")?.readText() ?: ""
        assertEquals(modelsContent, content.text)
    }
}
