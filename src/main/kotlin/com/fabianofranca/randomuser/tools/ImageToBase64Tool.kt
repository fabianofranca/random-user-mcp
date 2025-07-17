package com.fabianofranca.randomuser.tools

import com.fabianofranca.randomuser.ImageClient
import com.fabianofranca.randomuser.ImageClientImpl
import com.fabianofranca.randomuser.base.BaseTool
import com.fabianofranca.randomuser.dsl.string
import com.fabianofranca.randomuser.dsl.toolInput
import com.fabianofranca.randomuser.extensions.toImageToBase64Args
import com.fabianofranca.randomuser.models.ImageToBase64Args
import io.modelcontextprotocol.kotlin.sdk.CallToolRequest
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.TextContent
import io.modelcontextprotocol.kotlin.sdk.Tool
import java.net.URI
import java.net.URISyntaxException
import java.util.*

class ImageToBase64Tool(private val client: ImageClient = ImageClientImpl()) : BaseTool() {

    override fun tool() = Tool(
        name = "image_to_base64",
        description = "Fetches an image from a URL and returns it as a Base64 encoded string.",
        inputSchema = toolInput {
            properties {
                string(ImageToBase64Args::url.name) {
                    description("The URL of the image to fetch and encode.")
                }
            }
            required(ImageToBase64Args::url.name)
        }
    )

    override suspend fun handler(request: CallToolRequest): CallToolResult {
        val args = request.toImageToBase64Args()

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
