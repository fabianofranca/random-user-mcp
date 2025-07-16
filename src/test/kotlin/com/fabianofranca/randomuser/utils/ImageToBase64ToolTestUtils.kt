package com.fabianofranca.randomuser.utils

import com.fabianofranca.randomuser.models.ImageToBase64Args
import io.modelcontextprotocol.kotlin.sdk.CallToolRequest
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

object ImageToBase64ToolTestUtils {
    fun createRequest(url: String): CallToolRequest {
        val arguments = mutableMapOf<String, JsonPrimitive>()
        arguments[ImageToBase64Args::url.name] = JsonPrimitive(url)

        return CallToolRequest(
            name = "image_to_base64",
            arguments = JsonObject(arguments)
        )
    }
}
