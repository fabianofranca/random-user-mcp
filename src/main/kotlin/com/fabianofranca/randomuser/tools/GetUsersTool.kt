package com.fabianofranca.randomuser.tools

import com.fabianofranca.randomuser.RandomUserClient
import com.fabianofranca.randomuser.RandomUserClientImpl
import com.fabianofranca.randomuser.base.BaseTool
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
    private val client: RandomUserClient = RandomUserClientImpl(),
    private val json: Json = Json { ignoreUnknownKeys = true }
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
                    description("Nationality of users to be returned (default: ${GetUsersArgs.DEFAULT_NATIONALITY}). Available options by version: " +
                            "1.0: AU (Australia), BR (Brazil), CA (Canada), CH (Switzerland), DE (Germany), DK (Denmark), ES (Spain), FI (Finland), FR (France), GB (United Kingdom), IE (Ireland), IR (Iran), NL (Netherlands), NZ (New Zealand), TR (Turkey), US (United States); " +
                            "1.1: Added NO (Norway); " +
                            "1.2: Added IN (India); " +
                            "1.3: Added MX (Mexico), RS (Serbia); " +
                            "1.4: Added UA (Ukraine).")
                    default(GetUsersArgs.DEFAULT_NATIONALITY)
                }
                string(GetUsersArgs.PARAM_VERSION) {
                    description("API version to use (default: ${GetUsersArgs.DEFAULT_VERSION}). Available versions: 1.0, 1.1, 1.2, 1.3, 1.4")
                    default(GetUsersArgs.DEFAULT_VERSION)
                }
                string(GetUsersArgs.PARAM_SEED) {
                    description("Seed to use for generating consistent random results. Using the same seed will always return the same results.")
                }
                string(GetUsersArgs.PARAM_GENDER) {
                    description("Gender of users to return. Available options: male, female. If not specified, both genders will be returned.")
                }
                string(GetUsersArgs.PARAM_PASSWORD) {
                    description("Controls how passwords are generated. Format: CHARSETS,MIN_LENGTH-MAX_LENGTH or CHARSETS,MAX_LENGTH. " +
                            "Available charsets: special (special characters: !\"#$%&'()*+,- ./:;<=>?@[\\]^_`{|}~), upper (uppercase letters), lower (lowercase letters), number (digits). " +
                            "Example: 'upper,lower,1-16' generates passwords with uppercase and lowercase letters between 1-16 characters long. " +
                            "By default, passwords are chosen randomly from a list of ~10k top used passwords.")
                }
                string(GetUsersArgs.PARAM_INCLUDE) {
                    description("Comma-separated list of fields to include in the response. " +
                            "Available fields: gender, name, location, email, login, registered, dob, phone, cell, id, picture, nat. " +
                            "Example: 'gender,name,nat' will only return these fields and skip others, which can save processing time.")
                }
                string(GetUsersArgs.PARAM_EXCLUDE) {
                    description("Comma-separated list of fields to exclude from the response. " +
                            "Available fields: gender, name, location, email, login, registered, dob, phone, cell, id, picture, nat. " +
                            "Example: 'login' will return all fields except login data.")
                }
            }
        }
    )

    override suspend fun handler(request: CallToolRequest): CallToolResult {
        val args = request.toGetUsersArgs()

        return try {
            val response = client.getUsers(args)
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
