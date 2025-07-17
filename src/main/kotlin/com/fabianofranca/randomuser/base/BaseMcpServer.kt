package com.fabianofranca.randomuser.base

import io.ktor.utils.io.streams.*
import io.modelcontextprotocol.kotlin.sdk.Implementation
import io.modelcontextprotocol.kotlin.sdk.ServerCapabilities
import io.modelcontextprotocol.kotlin.sdk.server.Server
import io.modelcontextprotocol.kotlin.sdk.server.ServerOptions
import io.modelcontextprotocol.kotlin.sdk.server.StdioServerTransport
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlinx.io.asSink
import kotlinx.io.buffered

abstract class BaseMcpServer(name: String, version: String) {
    val server = Server(
        serverInfo = Implementation(
            name = name,
            version = version
        ),
        options = ServerOptions(
            capabilities = ServerCapabilities(
                tools = ServerCapabilities.Tools(false),
                resources = ServerCapabilities.Resources(
                    subscribe = false,
                    listChanged = false
                )
            )
        )
    )

    abstract val tools: List<BaseTool>
    abstract val resources: List<BaseResource>

    init {
        try {
            tools.forEach { it.addTool(server) }
            resources.forEach { it.addResource(server) }
        } catch (e: Exception) {
            // Log the exception but don't fail initialization
            println("Error initializing tools and resources: ${e.message}")
        }
    }

    fun startStdioServer() {
        runBlocking {
            val transport = StdioServerTransport(
                inputStream = System.`in`.asInput(),
                outputStream = System.out.asSink().buffered()
            )

            server.connect(transport)

            val done = Job()

            server.onClose {
                done.complete()
            }

            done.join()
        }
    }

    open fun onClose() {}
}
