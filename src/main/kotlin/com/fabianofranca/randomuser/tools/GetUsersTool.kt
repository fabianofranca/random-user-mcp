package com.fabianofranca.randomuser.tools

import com.fabianofranca.randomuser.RandomUserClient
import com.fabianofranca.randomuser.RandomUserClientImpl
import io.modelcontextprotocol.kotlin.sdk.CallToolRequest
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.TextContent
import io.modelcontextprotocol.kotlin.sdk.Tool
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class GetUsersTool(
    private val client: RandomUserClient = RandomUserClientImpl()
) : BaseTool() {
    override fun tool() = Tool(
        name = "get_users",
        description = "Returns the complete response from the randomuser.me API, including all user data.",
        inputSchema = Tool.Input(
            properties = buildJsonObject {
                put(RandomUserClient.PARAM_RESULTS, buildJsonObject {
                    put("type", "number")
                    put("description", "Number of users to return (default: ${RandomUserClient.DEFAULT_RESULTS}).")
                    put("default", RandomUserClient.DEFAULT_RESULTS)
                })
                put(RandomUserClient.PARAM_PAGE, buildJsonObject {
                    put("type", "number")
                    put("description", "Page of results to return (default: ${RandomUserClient.DEFAULT_PAGE}).")
                    put("default", RandomUserClient.DEFAULT_PAGE)
                })
                put(RandomUserClient.PARAM_NATIONALITY, buildJsonObject {
                    put("type", "string")
                    put(
                        "description",
                        "Nationality of users to be returned (default: ${RandomUserClient.DEFAULT_NATIONALITY})."
                    )
                    put("default", RandomUserClient.DEFAULT_NATIONALITY)
                })
            },
            required = listOf()
        )
    )

    override suspend fun handler(request: CallToolRequest): CallToolResult {
        val results = request.arguments[RandomUserClient.PARAM_RESULTS]?.toString()?.toIntOrNull()
            ?: RandomUserClient.DEFAULT_RESULTS
        val page =
            request.arguments[RandomUserClient.PARAM_PAGE]?.toString()?.toIntOrNull() ?: RandomUserClient.DEFAULT_PAGE
        val nationality = request.arguments[RandomUserClient.PARAM_NATIONALITY]?.toString()?.trim('"')
            ?: RandomUserClient.DEFAULT_NATIONALITY

        return try {
            val response = client.getUsers(results = results, page = page, nationality = nationality)
            val json = Json { prettyPrint = true }
            val responseJson = json.encodeToString(response)

            CallToolResult(
                content = listOf(
                    TextContent(responseJson)
                )
            )
        } catch (e: Exception) {
            CallToolResult(
                content = listOf(
                    TextContent("Error getting users: ${e.message}")
                )
            )
        }
    }
}
