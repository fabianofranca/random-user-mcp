package com.fabianofranca.randomuser.tools

import com.fabianofranca.randomuser.RandomUserClient
import com.fabianofranca.randomuser.RandomUserClientImpl
import com.fabianofranca.randomuser.dsl.number
import com.fabianofranca.randomuser.dsl.string
import com.fabianofranca.randomuser.dsl.toolInput
import io.modelcontextprotocol.kotlin.sdk.CallToolRequest
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.TextContent
import io.modelcontextprotocol.kotlin.sdk.Tool
import kotlinx.serialization.json.Json

class GetUsersTool(
    private val client: RandomUserClient = RandomUserClientImpl()
) : BaseTool() {
    override fun tool() = Tool(
        name = "get_users",
        description = "Returns the complete response from the randomuser.me API, including all user data.",
        inputSchema = toolInput {
            properties {
                number(RandomUserClient.PARAM_RESULTS) {
                    description("Number of users to return (default: ${RandomUserClient.DEFAULT_RESULTS}).")
                    default(RandomUserClient.DEFAULT_RESULTS)
                }
                number(RandomUserClient.PARAM_PAGE) {
                    description("Page of results to return (default: ${RandomUserClient.DEFAULT_PAGE}).")
                    default(RandomUserClient.DEFAULT_PAGE)
                }
                string(RandomUserClient.PARAM_NATIONALITY) {
                    description("Nationality of users to be returned (default: ${RandomUserClient.DEFAULT_NATIONALITY}).")
                    default(RandomUserClient.DEFAULT_NATIONALITY)
                }
            }
        }
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
