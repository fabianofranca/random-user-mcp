package com.fabianofranca.randomuser.resources

import io.modelcontextprotocol.kotlin.sdk.ReadResourceRequest
import io.modelcontextprotocol.kotlin.sdk.ReadResourceResult
import io.modelcontextprotocol.kotlin.sdk.TextResourceContents

class ModelsResource : BaseResource() {
    override val uri: String = "file:///models.md"
    override val name: String = "Random User MCP API Models"
    override val description: String = "This document describes the model structure that the Random User MCP API can respond with. The API integrates with the randomuser.me API and exposes functionalities as tools that can be consumed by AI agents and other MCP-compatible clients."
    override val mimeType: String = "text/markdown"

    override suspend fun handler(request: ReadResourceRequest): ReadResourceResult {
        val modelsContent = this::class.java.getResource("/models.md")?.readText() ?: "Models file not found"

        return ReadResourceResult(
            contents = listOf(
                TextResourceContents(
                    text = modelsContent,
                    uri = request.uri,
                    mimeType = "text/markdown"
                )
            )
        )
    }
}
