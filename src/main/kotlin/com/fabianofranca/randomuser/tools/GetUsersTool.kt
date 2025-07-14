package com.fabianofranca.randomuser.tools

import com.fabianofranca.randomuser.RandomUserClient
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.TextContent
import io.modelcontextprotocol.kotlin.sdk.Tool
import io.modelcontextprotocol.kotlin.sdk.server.Server
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

internal fun setupGetUsersTool(server: Server) {
    server.addTool(
        name = "get_users",
        description = "Retorna a resposta completa da API randomuser.me, incluindo todos os dados dos usuários.",
        inputSchema = Tool.Input(
            properties = buildJsonObject {
                put(RandomUserClient.PARAM_RESULTS, buildJsonObject {
                    put("type", "number")
                    put("description", "Número de usuários a retornar (padrão: ${RandomUserClient.DEFAULT_RESULTS}).")
                    put("default", RandomUserClient.DEFAULT_RESULTS)
                })
                put(RandomUserClient.PARAM_PAGE, buildJsonObject {
                    put("type", "number")
                    put("description", "Página de resultados a retornar (padrão: ${RandomUserClient.DEFAULT_PAGE}).")
                    put("default", RandomUserClient.DEFAULT_PAGE)
                })
                put(RandomUserClient.PARAM_NATIONALITY, buildJsonObject {
                    put("type", "string")
                    put("description", "Nacionalidade dos usuários a serem retornados (padrão: ${RandomUserClient.DEFAULT_NATIONALITY}).")
                    put("default", RandomUserClient.DEFAULT_NATIONALITY)
                })
            },
            required = listOf()
        )
    ) { request ->
        val results = request.arguments[RandomUserClient.PARAM_RESULTS]?.toString()?.toIntOrNull() ?: RandomUserClient.DEFAULT_RESULTS
        val page = request.arguments[RandomUserClient.PARAM_PAGE]?.toString()?.toIntOrNull() ?: RandomUserClient.DEFAULT_PAGE
        val nationality = request.arguments[RandomUserClient.PARAM_NATIONALITY]?.toString() ?: RandomUserClient.DEFAULT_NATIONALITY

        val client = RandomUserClient()

        try {
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
                    TextContent("Erro ao obter usuários: ${e.message}")
                )
            )
        }
    }
}
