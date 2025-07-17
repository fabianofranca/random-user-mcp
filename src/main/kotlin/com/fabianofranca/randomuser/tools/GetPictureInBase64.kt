package com.fabianofranca.randomuser.tools

import com.fabianofranca.randomuser.ImageClient
import com.fabianofranca.randomuser.ImageClientImpl
import com.fabianofranca.randomuser.base.BaseTool
import com.fabianofranca.randomuser.dsl.string
import com.fabianofranca.randomuser.dsl.toolInput
import com.fabianofranca.randomuser.extensions.toGetPictureInBase64Args
import com.fabianofranca.randomuser.models.GetPictureInBase64Args
import io.modelcontextprotocol.kotlin.sdk.CallToolRequest
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.TextContent
import io.modelcontextprotocol.kotlin.sdk.Tool
import java.net.URI
import java.net.URISyntaxException
import java.util.*

class GetPictureInBase64(private val client: ImageClient = ImageClientImpl()) : BaseTool() {

    override fun tool() = Tool(
        name = "get_picture_in_base64",
        description = """
        Retrieves and converts a RandomUser.me profile image into a Base64 string. 
        Accepts only valid RandomUser.me URLs (http/https) in the following formats:
        - https://randomuser.me/api/portraits/[men|women]
        - https://randomuser.me/api/portraits/med/[men|women]
        - https://randomuser.me/api/portraits/thumb/[men|women]
        
        Returns: A Base64 encoded string of the image or an error message if the URL is invalid.
    """.trimIndent(),
        inputSchema = toolInput {
            properties {
                string(GetPictureInBase64Args::url.name) {
                    description("Complete RandomUser.me image URL to fetch and encode in Base64.")
                }
            }
            required(GetPictureInBase64Args::url.name)
        }
    )

    override suspend fun handler(request: CallToolRequest): CallToolResult {
        val args = request.toGetPictureInBase64Args()

        return try {
            if (!isValidImageUrl(args.url)) {
                return CallToolResult(content = listOf(TextContent("Invalid image URL format")))
            }

            if (!isRandomUserImageUrl(args.url)) {
                return CallToolResult(content = listOf(TextContent("Not a valid RandomUser image URL")))
            }

            val imageBytes = client.getImage(args.url)
            val base64Image = Base64.getEncoder().encodeToString(imageBytes)
            CallToolResult(content = listOf(TextContent(base64Image)))
        } catch (e: Exception) {
            CallToolResult(content = listOf(TextContent("Error fetching or encoding image: ${e.message}")))
        }
    }

    private fun isValidImageUrl(url: String): Boolean {
        return try {
            val uri = URI(url)
            (uri.scheme == "http" || uri.scheme == "https")
        } catch (_: URISyntaxException) {
            false
        }
    }

    private fun isRandomUserImageUrl(url: String): Boolean {
        return VALID_PATHS.any { url.startsWith(it) }
    }

    companion object {
        private const val BASE_URL = "https://randomuser.me/api/portraits"
        private val VALID_PATHS = listOf(
            "$BASE_URL/men",
            "$BASE_URL/med/men",
            "$BASE_URL/thumb/men",
            "$BASE_URL/women",
            "$BASE_URL/med/women",
            "$BASE_URL/thumb/women"
        )
    }
}