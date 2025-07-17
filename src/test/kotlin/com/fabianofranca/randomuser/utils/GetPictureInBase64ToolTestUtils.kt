package com.fabianofranca.randomuser.utils

import com.fabianofranca.randomuser.models.GetPictureInBase64Args
import io.modelcontextprotocol.kotlin.sdk.CallToolRequest
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

object GetPictureInBase64ToolTestUtils {
    fun createRequest(url: String): CallToolRequest {
        val arguments = mutableMapOf<String, JsonPrimitive>()
        arguments[GetPictureInBase64Args::url.name] = JsonPrimitive(url)

        return CallToolRequest(
            name = "get_picture_in_base64",
            arguments = JsonObject(arguments)
        )
    }
}
