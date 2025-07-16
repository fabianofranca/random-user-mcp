package com.fabianofranca.randomuser.extensions

import com.fabianofranca.randomuser.models.GetUsersArgs
import com.fabianofranca.randomuser.models.ImageToBase64Args
import io.modelcontextprotocol.kotlin.sdk.CallToolRequest
import kotlinx.serialization.json.Json

/**
 * Extension function for CallToolRequest to convert arguments to GetUsersArgs.
 * Uses deserialization as the conversion method.
 *
 * @return An instance of GetUsersArgs with values from the arguments
 */
fun CallToolRequest.toGetUsersArgs(): GetUsersArgs {
    val json = Json { ignoreUnknownKeys = true }
    return json.decodeFromJsonElement(GetUsersArgs.serializer(), arguments)
}

/**
 * Extension function for CallToolRequest to convert arguments to ImageToBase64Args.
 * Uses deserialization as the conversion method.
 *
 * @return An instance of ImageToBase64Args with values from the arguments
 */
fun CallToolRequest.toImageToBase64Args(): ImageToBase64Args {
    val json = Json { ignoreUnknownKeys = true }
    return json.decodeFromJsonElement(ImageToBase64Args.serializer(), arguments)
}
