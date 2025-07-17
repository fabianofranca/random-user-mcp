package com.fabianofranca.randomuser.extensions

import com.fabianofranca.randomuser.models.GetPictureInBase64Args
import com.fabianofranca.randomuser.models.GetUsersArgs
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
 * Extension function for CallToolRequest to convert arguments to GetPictureInBase64Args.
 * Uses deserialization as the conversion method.
 *
 * @return An instance of GetPictureInBase64Args with values from the arguments
 */
fun CallToolRequest.toGetPictureInBase64Args(): GetPictureInBase64Args {
    val json = Json { ignoreUnknownKeys = true }
    return json.decodeFromJsonElement(GetPictureInBase64Args.serializer(), arguments)
}
