package com.fabianofranca.randomuser.tools

import com.fabianofranca.randomuser.RandomUserClient
import com.fabianofranca.randomuser.RandomUserClientImpl
import com.fabianofranca.randomuser.dsl.number
import com.fabianofranca.randomuser.dsl.string
import com.fabianofranca.randomuser.dsl.toolInput
import com.fabianofranca.randomuser.extensions.toGetUsersArgs
import com.fabianofranca.randomuser.models.GetUsersArgs
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
                number(GetUsersArgs.PARAM_RESULTS) {
                    description("Number of users to return (default: ${GetUsersArgs.DEFAULT_RESULTS}).")
                    default(GetUsersArgs.DEFAULT_RESULTS)
                }
                number(GetUsersArgs.PARAM_PAGE) {
                    description("Page of results to return (default: ${GetUsersArgs.DEFAULT_PAGE}).")
                    default(GetUsersArgs.DEFAULT_PAGE)
                }
                string(GetUsersArgs.PARAM_NATIONALITY) {
                    description("Nationality of users to be returned (default: ${GetUsersArgs.DEFAULT_NATIONALITY}).")
                    default(GetUsersArgs.DEFAULT_NATIONALITY)
                }
            }
        }
    )

    override suspend fun handler(request: CallToolRequest): CallToolResult {
        val args = request.toGetUsersArgs()

        return try {
            val response = client.getUsers(args)
            val json = Json {}
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
