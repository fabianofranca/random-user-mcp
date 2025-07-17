package com.fabianofranca.randomuser.base

import io.modelcontextprotocol.kotlin.sdk.CallToolRequest
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.Tool
import io.modelcontextprotocol.kotlin.sdk.server.RegisteredTool
import io.modelcontextprotocol.kotlin.sdk.server.Server

abstract class BaseTool {
    abstract fun tool(): Tool

    abstract suspend fun handler(request: CallToolRequest): CallToolResult

    fun addTool(server: Server) {
        server.addTools(
            listOf(
                RegisteredTool(
                    tool = tool(),
                    handler = ::handler
                )
            )
        )
    }
}