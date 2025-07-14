package com.fabianofranca.randomuser

import com.fabianofranca.randomuser.tools.setupGetUsersTool
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

class RandomUserMcpServer {
    val server = Server(
        serverInfo = Implementation(
            name = "names-server",
            version = "0.0.1"
        ),
        options = ServerOptions(
            capabilities = ServerCapabilities(
                tools = ServerCapabilities.Tools(false)
            )
        )
    )

    init {
        setupTools()
    }

    private fun setupTools() {
        setupGetUsersTool(server)
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
}
