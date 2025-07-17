package com.fabianofranca.randomuser.base

import io.modelcontextprotocol.kotlin.sdk.ReadResourceRequest
import io.modelcontextprotocol.kotlin.sdk.ReadResourceResult
import io.modelcontextprotocol.kotlin.sdk.server.Server

abstract class BaseResource {
    abstract val uri: String
    abstract val name: String
    abstract val description: String
    abstract val mimeType: String

    abstract suspend fun handler(request: ReadResourceRequest): ReadResourceResult

    fun addResource(server: Server) {
        server.addResource(
            uri = uri,
            name = name,
            description = description,
            mimeType = mimeType,
            readHandler = ::handler
        )
    }
}