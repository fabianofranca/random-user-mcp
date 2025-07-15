package com.fabianofranca.randomuser.tools

import com.fabianofranca.randomuser.dsl.toolInput
import io.modelcontextprotocol.kotlin.sdk.CallToolRequest
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.TextContent
import io.modelcontextprotocol.kotlin.sdk.Tool

class GetModelsDocTool : BaseTool() {
    override fun tool() = Tool(
        name = "get_models_doc",
        description = "Returns the documentation for the models used in the Random User API.",
        inputSchema = toolInput {  }
    )

    override suspend fun handler(request: CallToolRequest): CallToolResult {
        val modelsContent = this::class.java.getResource("/models.md")?.readText() ?: "Models file not found"

        return CallToolResult(
            content = listOf(
                TextContent(modelsContent)
            )
        )
    }
}
