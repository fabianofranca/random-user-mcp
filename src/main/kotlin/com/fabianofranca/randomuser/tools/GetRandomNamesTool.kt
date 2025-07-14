package com.fabianofranca.randomuser.tools

import com.fabianofranca.randomuser.RandomUserClient
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.TextContent
import io.modelcontextprotocol.kotlin.sdk.Tool
import io.modelcontextprotocol.kotlin.sdk.server.Server
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

internal fun setupGetRandomNamesTool(server: Server) {
    server.addTool(
        name = "get_random_names",
        description = "Obtém uma lista de nomes aleatórios de usuários de uma API externa, com suporte a paginação e nacionalidade.",
        inputSchema = Tool.Input(
            properties = buildJsonObject {
                put("limit", buildJsonObject {
                    put("type", "number")
                    put("description", "Número máximo de nomes a retornar (padrão: 10).")
                    put("default", 10)
                })
                put("offset", buildJsonObject {
                    put("type", "number")
                    put("description", "Número de nomes a pular antes de retornar (padrão: 0).")
                    put("default", 0)
                })
                put("nationality", buildJsonObject {
                    put("type", "string")
                    put("description", "Nacionalidade dos nomes a serem retornados (padrão: BR).")
                    put("default", "BR")
                })
            },
            required = listOf()
        )
    ) { request ->
        val limit = request.arguments["limit"]?.toString()?.toIntOrNull() ?: 10
        val offset = request.arguments["offset"]?.toString()?.toIntOrNull() ?: 0
        val nationality = request.arguments["nationality"]?.toString() ?: "BR"

        val client = RandomUserClient()
        val page = (offset / limit) + 1

        try {
            val response = client.getUsers(results = limit, page = page, nationality = nationality)
            val names = response.results.map { "${it.name.first} ${it.name.last}" }
            CallToolResult(
                content = listOf(
                    TextContent("Lista de nomes aleatórios (${names.size}): ${names.joinToString(", ")}")
                )
            )
        } catch (e: Exception) {
            CallToolResult(
                content = listOf(
                    TextContent("Erro ao obter nomes aleatórios: ${e.message}")
                )
            )
        }
    }
}
